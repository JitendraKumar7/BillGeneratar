package com.agira.bhinfotech.lib.pdf;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;

import com.agira.bhinfotech.lib.pdf.interfaces.Callback;
import com.agira.bhinfotech.lib.pdf.utils.BitmapUtils;
import com.agira.bhinfotech.lib.pdf.viewRenderer.AbstractViewRenderer;
import com.agira.bhinfotech.utility.Utility;
import com.pdfjet.Image;
import com.pdfjet.ImageType;
import com.pdfjet.PDF;
import com.pdfjet.Page;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.os.Environment.getExternalStoragePublicDirectory;

/**
 * a PDF document creator
 *
 * @author Jitendra Kumar
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class CreateDocument implements Callback {

    private File _file;

    private Context _ctx;

    private Thread _thread = null;

    private Exception _error = null;

    private boolean _isWorking = false;

    private ProgressDialog _ringProgressDialog;

    private ArrayList<InputStream> _pages_rendered;

    private ArrayList<AbstractViewRenderer> _pages;

    private Handler _handler = new Handler();


    /**
     * dispose the item
     */
    public void dispose() {
        release();

        _thread = null;
        _handler = null;
        _ringProgressDialog = null;
    }

    /**
     * release this class for future usage
     */
    private void release() {
        _pages.clear();
        _pages_rendered.clear();

        _file = null;
        _error = null;
        _isWorking = false;

        if (_ringProgressDialog != null) {
            _ringProgressDialog.dismiss();
            _ringProgressDialog = null;
        }
    }

    /**
     * clear all of the pages and rendered pages
     */
    private void clearPages() {
        _pages.clear();
        _pages_rendered.clear();
    }

    /**
     * does the a PDF is generating now?
     *
     * @return {@code true/false}
     */
    private boolean isWorking() {

        return _isWorking;
    }

    protected CreateDocument(Context ctx) {

        _ctx = ctx;
        _pages = new ArrayList<>();
        _pages_rendered = new ArrayList<>();
    }

    /**
     * add a page with a custom class view renderer. please note that <b>the bitmap of the view will be recycled.</b>
     *
     * @param page a view renderer instance
     * @see AbstractViewRenderer
     */
    public void addPage(AbstractViewRenderer page) {

        _pages.add(page);
    }

    /**
     * create the pdf and render according to report types and a time frame
     */
    public void createPdf(final float[] orientation) {
        if (isWorking())
            return;

        Resources res = _ctx.getResources();

        if (_ctx != null) {
            if (_ringProgressDialog != null) {
                _ringProgressDialog.dismiss();
            }

            String _txtProgressTitle = "Please wait", _txtProgressMessage = "Generating PDF...";
            _ringProgressDialog = ProgressDialog.show(_ctx, _txtProgressTitle, _txtProgressMessage, true);

            if (!_ringProgressDialog.isShowing())
                _ringProgressDialog.show();
        }

        _thread = new Thread(new Runnable() {
            @Override
            public void run() {
                _isWorking = true;

                for (AbstractViewRenderer view : _pages) {
                    Utility.log_d("render page");
                    renderView(view);
                }

                internal_generatePdf(orientation);

                //Utility.log_d("pdf 1");

                clearPages();

                //Utility.log_d("pdf 2");

                // go back to the main thread
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (_ringProgressDialog != null)
                            _ringProgressDialog.dismiss();

                        if (_error != null) onError(_error);
                        else onComplete(_file);

                        Utility.log_d("complete");

                        release();
                    }
                });

            }

        });

        _thread.setPriority(Thread.MAX_PRIORITY);
        _thread.start();
    }

    /**
     * render the view
     *
     * @param page {@link AbstractViewRenderer} instance
     */
    private void renderView(AbstractViewRenderer page) {

        Bitmap bmp = page.render(page.getView());
        ByteArrayInputStream stream = BitmapUtils.bitmapToPngInputStream(bmp);

        page.disposeBitmap();

        _pages_rendered.add(stream);
    }

    private void internal_generatePdf(float[] orientation) {

        File dir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        _file = new File(dir, getInvoiceNo());
        _error = null;

        try {
            FileOutputStream fos = new FileOutputStream(_file);
            PDF pdf = new PDF(fos);

            float ar;
            Page page;
            Image image;

            pdf.setTitle("BH InfoTech");
            pdf.setAuthor("Jatin Kumar");
            pdf.setSubject("Tax Invoice");

            for (InputStream inputStream : _pages_rendered) {

                page = new Page(pdf, orientation);
                image = new Image(pdf, inputStream, ImageType.PNG);

                Utility.log_d("add page");

                inputStream.close();

                ar = page.getWidth() / image.getWidth();

                image.scaleBy(ar);

                image.drawOn(page);
            }

            pdf.flush();
            fos.close();
        } catch (Exception exc) {
            _error = exc;
        }

    }


    private String getInvoiceNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_kkmmss", Locale.US);
        return String.format(Locale.US, "BHI_%s.pdf", sdf.format(Calendar.getInstance().getTime()));
    }

}
