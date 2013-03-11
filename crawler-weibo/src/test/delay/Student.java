package test.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ģ�⿼�ԣ�ʱ��Ϊ120���ӣ�ѧ��������30���Ӻ󽻾� ��ѧ���������� �� ʱ�䵽�߿��Խ���
 * 
 * @author Сe
 * 
 *         2010-4-30 ����11:14:25
 */
class Student implements Runnable, Delayed {
	private String name;
	private long submitTime;// ����ʱ��
	private long workTime;// ����ʱ��

	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(String name, long submitTime) {
		super();
		this.name = name;
		workTime = submitTime;
		// ��תΪתΪns
		this.submitTime = TimeUnit.NANOSECONDS.convert(submitTime,
				TimeUnit.MILLISECONDS) + System.nanoTime();
	}

	@Override
	public void run() {
		System.out.println(name + " ����,��ʱ" + workTime / 100 + "����");
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(submitTime - System.nanoTime(),
				TimeUnit.NANOSECONDS);
	}

	@Override
	public int compareTo(Delayed o) {
		Student that = (Student) o;
		return submitTime > that.submitTime ? 1
				: (submitTime < that.submitTime ? -1 : 0);
	}

	public static class EndExam extends Student {
		private ExecutorService exec;

		public EndExam(int submitTime, ExecutorService exec) {
			super(null, submitTime);
			this.exec = exec;
		}

		@Override
		public void run() {
			exec.shutdownNow();
		}
	}

}
