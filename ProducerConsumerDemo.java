class Buffer {
    private int data;
    private boolean hasData = false;

    public synchronized void put(int value) throws InterruptedException {
        while (hasData) {
            wait();
        }
        data = value;
        hasData = true;
        System.out.println(Thread.currentThread().getName() + " Produced: " + data);
        notifyAll();
    }

    public synchronized int get() throws InterruptedException {
        while (!hasData) {
            wait();
        }
        hasData = false;
        System.out.println(Thread.currentThread().getName() + " Consumed: " + data);
        notifyAll();
        return data;
    }
}

class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                buffer.put(i);
                Thread.sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                buffer.get();
                Thread.sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        
        Thread producer1 = new Thread(new Producer(buffer), "Producer-1");
        Thread producer2 = new Thread(new Producer(buffer), "Producer-2");
        Thread consumer1 = new Thread(new Consumer(buffer), "Consumer-1");
        Thread consumer2 = new Thread(new Consumer(buffer), "Consumer-2");

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
    }
}
