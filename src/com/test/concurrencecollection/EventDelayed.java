package com.test.concurrencecollection;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


/**
 * this is the helpful class for DelayQueue
 * */
public class EventDelayed implements Delayed {
		private Date startDate;
		
		public EventDelayed(Date startDate) {
			this.startDate = startDate;
		}
		
		public long getDelay(TimeUnit unit) {
			Date now = new Date();
			long diff = startDate.getTime() - now.getTime();
			return unit.convert(diff, TimeUnit.MILLISECONDS);
		}
		
		public int compareTo(Delayed o) {
			long result = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
			if (result < 0) {
				return -1;
			} else if (result > 0) {
				return 1;
			}
			return 0;
		}
}
