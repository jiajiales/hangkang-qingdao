package com.hotcomm.data.view.cview;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeviceView {

	public static final String DEVICE_IMPORT_PAGE = "service/device-import";

	public static final String DEVICE_EXPORT_PAGE = "service/device-export";

	public static final String DEVICE_BA_DEVICEGROUP_PAGE = "service/device-ba-deviceGroup";

	public static final String DEVICE_DATA_VIEW_PAGE = "service/device-data-view";

	@GetMapping("/service/deviceImport")
	public String deviceImport(Model model) {
		return DEVICE_IMPORT_PAGE;
	}

	@GetMapping("/service/deviceExport")
	public String deviceExport(Model model) {
		return DEVICE_EXPORT_PAGE;
	}

	@GetMapping("/service/baDeviceGroup")
	public String baDeviceGroup(Model model) {
		return DEVICE_BA_DEVICEGROUP_PAGE;
	}

	@GetMapping("/service/deviceDataView")
	public String deviceView(Model model) {
		return DEVICE_DATA_VIEW_PAGE;
	}

}
