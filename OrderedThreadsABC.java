public class OrderedThreadsABC {

    private static volatile int indicator = 1;
    private static final Object mon = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (mon) {
                        while (indicator != 1) mon.wait();
                        System.out.print("A");
                        indicator = 2;
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (mon) {
                        while (indicator != 2) mon.wait();
                        System.out.print("B");
                        indicator = 3;
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (mon) {
                        while (indicator != 3) mon.wait();
                        System.out.print("C ");
                        indicator = 1;
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
