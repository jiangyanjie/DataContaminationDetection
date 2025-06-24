package info.narazaki.android.tuboroid.op;

import info.narazaki.android.tuboroid.TuboroidApplication.AccPrefs;
import info.narazaki.android.tuboroid.data.EData;
import info.narazaki.android.tuboroid.data.DData;
import java.util.concurrent.FutureTask;

abstract public class UET {
    private static final String LT = "UET"; 
    protected final TManager um;
    protected FutureTask<?> sh;

    protected UET(TManager um) {
        super();
        this.um = um;
        this.sh = null;
    }

    static public interface WCallback {
        void WPS();
        void WPF(final String fm);
        void NH(final boolean nf);
        void RW(final EData red, final String ram);
    }

    public class UPEntry {
        public void PT() {
            if (sh != null) {
                sh.cancel(true);
            }
        }
    }
}