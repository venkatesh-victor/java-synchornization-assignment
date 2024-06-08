class Printer {
    public void printDocument(String documentName) {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " is printing: " + documentName);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " has finished printing: " + documentName);
        }
    }
}

class PrintJob implements Runnable {
    private Printer printer;
    private String[] documents;

    public PrintJob(Printer printer, String[] documents) {
        this.printer = printer;
        this.documents = documents;
    }

    @Override
    public void run() {
        for (String document : documents) {
            printer.printDocument(document);
        }
    }
}

public class SynchronizedPrinterDemo {
    public static void main(String[] args) {
        Printer sharedPrinter = new Printer();
        
        String[] documents1 = {"Doc1", "Doc2", "Doc3"};
        String[] documents2 = {"Doc4", "Doc5", "Doc6"};

        Thread thread1 = new Thread(new PrintJob(sharedPrinter, documents1), "Thread-1");
        Thread thread2 = new Thread(new PrintJob(sharedPrinter, documents2), "Thread-2");

        thread1.start();
        thread2.start();
    }
}
