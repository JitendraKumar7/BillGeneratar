package angira.bhinfotech.library.pdf.interfaces;

import java.io.File;

public interface Callback {

    /**
     * error creating the PDF
     */
    void onError(Exception e);

    /**
     * successful completion of pdf
     *
     * @param file the file
     */
    void onComplete(File file);

}
