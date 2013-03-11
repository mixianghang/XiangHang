package test;

import java.util.ArrayList;
import java.util.List;

public class TestList {

	/**
	 * TODO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		if (ids != null && ids.size() > 1) {
			for (Long id : ids) {
				if (id.equals(1L)) {
					ids.remove(id);
					System.out.println("sadffff");
					break;
				}
			}
		}
		System.out.println(ids);
		System.out.println("111_63255111.properties".replaceAll("\\d*_", "")
				.replaceAll(".properties", ""));

	}

}
