package pro.sky.java.course3;

public class Main {

    public Integer count = 0;

    public Object flag = new Object();

    public static void main(String[] args) {
        Main main = new Main();

        main.doOperation(0);

        Thread daemon = main.startDaemon();

        main.doOperation(1);

        Thread thread = new Thread(() -> {
            main.doOperation(2);
            if (Thread.currentThread().isInterrupted()) {
                throw new RuntimeException();
            }
            main.doOperation(3);

            if (!Thread.currentThread().isInterrupted()) {
//               throw new RuntimeException(); если без !
                main.doOperation(4);
            }
            main.doOperation(4);
        });
        thread.start();

        new Thread(() -> {
            main.doOperation(5);
            main.doOperation(6);
            main.doOperation(7);
        }).start();

        thread.interrupt();

        main.doOperation(8);
        main.doOperation(9);


        daemon.interrupt();
    }

    public  void doOperation(int number) {
        synchronized (flag) {
            System.out.println("Operation " + number + " Count " + count);
            count++;
        }
        String s = "";
//        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100_000; i++) {
            s += i;
//            sb.append(i);
        }
//        sb.toString();
//        System.out.println(sb);

    }

    public Thread startDaemon() {
        Thread daemon = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Daemon running");
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                System.out.println("Daemon interrupted");
            }
        });
        daemon.start();
        return daemon;
    }
}