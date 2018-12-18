package angira.bhinfotech.library.pdf.viewRenderer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agira.bhinfotech.utility.Utility;

/**
 * @author Tomer Shalev
 */
public class AbstractViewRenderer {

    private View _view = null;
    private Bitmap _bmp = null;
    private Context _ctx = null;

    /**
     * dispose the item
     */
    public void dispose() {
        _bmp.recycle();
        _view = null;
        _ctx = null;
    }

    /**
     * validate the input
     *
     * @throws IllegalArgumentException if context or view is null
     */
    private void validate() {
        if (_ctx == null)
            throw new IllegalArgumentException("ViewRenderer:: context was not set!!");
        if (_view == null)
            throw new IllegalArgumentException("ViewRenderer:: view or layout resource was not set!!");
    }

    /**
     * dispose the bitmap
     */
    public void disposeBitmap() {
        if (_bmp != null) _bmp.recycle();
        _bmp = null;
    }

    /**
     * get the view to render
     *
     * @return the view
     */
    public View getView() {
        return _view;
    }

    /**
     * render the bitmap
     *
     * <h1>Note: </h1>
     * on <b>API <= 17</b>, you must give explicit {@code width} and {@code height} because of a bug in {@link android.widget.RelativeLayout}
     */
    final public Bitmap render(View view) {
        validate();

        int UNSPECIFIED = View.MeasureSpec.UNSPECIFIED;
        int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

        @SuppressLint("Range") int specWidth = View.MeasureSpec.makeMeasureSpec(WRAP_CONTENT, UNSPECIFIED);
        @SuppressLint("Range") int specHeight = View.MeasureSpec.makeMeasureSpec(WRAP_CONTENT, UNSPECIFIED);
        try {
            view.measure(specWidth, specHeight);
        } catch (NullPointerException exc) {
            exc.printStackTrace();
        }

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Utility.log_d("Height-" + view.getHeight() + ", Width-" + view.getWidth());


        disposeBitmap();
        Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());

        view.draw(c);

        return _bmp = b;
    }

    /**
     * get the width to view
     *
     * @return the width
     */
    public int getWidth() {
        return getView().getWidth();
    }

    /**
     * get the height to view
     *
     * @return the height
     */
    public int getHeight() {
        return getView().getHeight();
    }

    /**
     * @param ctx  a context
     * @param view an inflated view
     */
    public AbstractViewRenderer(Context ctx, View view) {

        _ctx = ctx;
        _view = view;
    }

    /**
     * @param ctx         a context
     * @param layoutResId a layout resource id
     */
    public AbstractViewRenderer(Context ctx, int layoutResId) {
        this(ctx, LayoutInflater.from(ctx).inflate(layoutResId, null));
    }

}
