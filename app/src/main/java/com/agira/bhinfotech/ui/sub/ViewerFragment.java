package com.agira.bhinfotech.ui.sub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;

import com.agira.bhinfotech.R;
import com.agira.bhinfotech.lib.pdf.CreateDocument;
import com.agira.bhinfotech.lib.pdf.viewRenderer.AbstractViewRenderer;
import com.agira.bhinfotech.ui.base.BaseFragment;
import com.agira.bhinfotech.ui.dio.EmailDialogFragment;
import com.agira.bhinfotech.ui.dio.MobileDialogFragment;
import com.agira.bhinfotech.utility.Utility;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class ViewerFragment extends BaseFragment implements OnRenderListener,
        OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    @SuppressLint("StaticFieldLeak")
    static AbstractViewRenderer page;

    Uri apkURI;
    Uri fileURI;

    String mFilePath;

    String pdfFileName;

    Integer pageNumber = 0;

    @BindView(R.id.pdfView)
    PDFView pdfView;

    @OnClick({R.id.btnPrint,
            R.id.btnEmail,
            R.id.btnWhatsApp})
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnPrint:
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setPackage("com.dynamixsoftware.printershare");
                    i.setDataAndType(fileURI, "application/pdf");
                    startActivity(i);
                } catch (Exception e) {
                    Utility.log_d(e.getMessage(), e);
                }
                break;

            case R.id.btnEmail: {
                EmailDialogFragment.getInstance(fileURI)
                        .show(getChildFragmentManager(), "Diag");
                break;
            }

            case R.id.btnWhatsApp: {
                MobileDialogFragment.getInstance(apkURI)
                        .show(getChildFragmentManager(), "Diag");
                break;
            }

        }
    }

    public String getFileName(Uri uri) {
        fileURI = uri;
        String result = null;
        if (Objects.equals(uri.getScheme(), "content")) {
            try (Cursor cursor = _activity.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
            Utility.log_d("Content Result " + result);
        }
        if (result == null) {
            result = uri.getLastPathSegment();
            Utility.log_d("File Result " + result);
        }
        return result;
    }

    private void displayFromUri(Uri uri) {
        pdfFileName = getFileName(uri);

        pdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(_activity))
                .spacing(10)
                .onRender(this)
                .onPageError(this)
                .load();
    }

    @Override
    public void loadComplete(int nbPages) {

        PdfDocument.Meta meta = pdfView.getDocumentMeta();

        Utility.log_d("title = " + meta.getTitle());
        Utility.log_d("author = " + meta.getAuthor());
        Utility.log_d("subject = " + meta.getSubject());
        Utility.log_d("creator = " + meta.getCreator());
        Utility.log_d("modDate = " + meta.getModDate());
        Utility.log_d("keywords = " + meta.getKeywords());
        Utility.log_d("producer = " + meta.getProducer());
        Utility.log_d("creationDate = " + meta.getCreationDate());
    }

    @Override
    public void onPageError(int page, Throwable e) {

        Utility.log_d("Cannot load page " + page, e);
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

        pageNumber = page;
        Utility.log_d(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    @Override
    protected void onCreate(@NonNull LayoutInflater inflater) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utility.log_d("onCreate");
        CreateDocument document = new CreateDocument(_activity) {

            @Override
            public void onComplete(File file) {
                displayFromUri(Uri.fromFile(file));
                mFilePath = file.getAbsolutePath();

                Utility.log_d("Complete " + mFilePath);
                String authority = _activity.getPackageName() + ".provider";
                apkURI = FileProvider.getUriForFile(_activity, authority, file);
            }

            @Override
            public void onError(Exception e) {

                Utility.log_d(e.getMessage(), e);
            }

        };

        document.addPage(page);
        document.createPdf(new float[]{595.0F, page.getHeight()});
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_viewer;
    }

    public static Fragment getInstance(AbstractViewRenderer _page) {
        ViewerFragment.page = _page;
        return new ViewerFragment();
    }

    public void onInitiallyRendered(int pages, float width, float height) {
        pdfView.fitToWidth(); // optionally pass page number
    }

}
