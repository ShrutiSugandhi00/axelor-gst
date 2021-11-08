package com.axelor.gst.service;

import com.axelor.app.AxelorModule;

public class GstModule extends AxelorModule {
	@Override
	protected void configure() {
		bind(GstService.class).to(GstServiceImpl.class);
	}

}
