package com.jesus.coupons.init_threads;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jesus.coupons.logic.CouponsController;


@Component
public class ExpiredCouponsHandler implements Runnable {

	
	@Autowired
	private CouponsController couponsController;
	
	private Thread thread;
	
	
	@PostConstruct
	private void init() {
		this.thread = new Thread(this);
        this.thread.start();
	}
	
	
	@Override
	public void run() {

		this.couponsController.deleteExpiredCoupons();
		
		while (true) {
			int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			if (currentHour == 00) {
				this.couponsController.deleteExpiredCoupons();
				//Since the current hour is 00, there is no point in checking the hour every minute.
				//Therefore, raising the sleep timer to 22 hours for a single loop.
				try {
					Thread.sleep(79200000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
