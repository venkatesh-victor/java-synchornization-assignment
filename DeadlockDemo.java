class ResourceA {
    public synchronized void lockResourceB(ResourceB resourceB) {
        System.out.println(Thread.currentThread().getName() + " locked ResourceA");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(Thread.currentThread().getName() + " attempting to lock ResourceB");
        synchronized (resourceB) {
            System.out.println(Thread.currentThread().getName() + " locked ResourceB");
        }
    }
}

class ResourceB {
    public synchronized void lockResourceA(ResourceA resourceA) {
        System.out.println(Thread.currentThread().getName() + " locked ResourceB");
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println(Thread.currentThread().getName() + " attempting to lock ResourceA");
        synchronized (resourceA) {
            System.out.println(Thread.currentThread().getName() + " locked ResourceA");
        }
    }
}

public class DeadlockDemo {
    public static void main(String[] args) {
        ResourceA resourceA = new ResourceA();
        ResourceB resourceB = new ResourceB();
        
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                resourceA.lockResourceB(resourceB);
            }
        }, "Thread-1");
        
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                resourceB.lockResourceA(resourceA);
            }
        }, "Thread-2");
        
        thread1.start();
        thread2.start();
    }
}
