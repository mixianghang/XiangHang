package test.delay;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;

class Teacher implements Runnable {
	private DelayQueue<Student> students;

	public Teacher(DelayQueue<Student> students, ExecutorService exec) {
		super();
		this.students = students;
	}

	@Override
	public void run() {
		try {
			System.out.println("���Կ�ʼ����");
			while (!Thread.interrupted()) {
				students.take().run();
			}
			System.out.println("���Խ�������");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
