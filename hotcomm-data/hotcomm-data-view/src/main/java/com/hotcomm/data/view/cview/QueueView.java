package com.hotcomm.data.view.cview;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class QueueView extends AuthView {

	public static final String QUEUE_PAGE = "service/queue";
	public static final String QUEUE_MESSAGE_PAGE = "service/queue-message";
	public static final String QUEUE_GET = "service/queue-get";
	public static final String QUEUE_UPDATE_PAGE = "service/queue-update";
	public static final String QUEUE_RENAME = "service/queue-rename";
	public static final String QUEUE_UPDATE_HOLETIME = "service/queue-update-holeTime";
	public static final String QUEUE_UPDATE_FILTERNUMS = "service/queue-update-filternums";
	public static final String VIEW_KEY = "service-queue";

	@GetMapping("/service/queue")
	public String queue(HttpServletRequest request, Model model) throws JsonProcessingException {
		storeAuthButtons(request, model, VIEW_KEY);
		return QUEUE_PAGE;
	}

	@GetMapping("/service/queue/message")
	public ModelAndView queueMessage(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		Object memberId = session.getAttribute("memberId");

		if (memberId != null) {
			model.put("memberId", memberId);
		}

		return new ModelAndView(QUEUE_MESSAGE_PAGE, model);
	}

	@GetMapping("/service/queueGet")
	public String queueGet(Model model) {
		return QUEUE_GET;
	}

	@GetMapping("/service/queueUpdate")
	public String queueUpdate(Model model) {
		return QUEUE_UPDATE_PAGE;
	}

	@GetMapping("/service/queueRename")
	public String queueRename(Model model) {
		return QUEUE_RENAME;
	}

	@GetMapping("/service/queueUpdateHoleTime")
	public String queueUpdateHoleTime(Model model) {
		return QUEUE_UPDATE_HOLETIME;
	}

	@GetMapping("/service/queueUpdateFilternums")
	public String queueUpdateFilternums(Model model) {
		return QUEUE_UPDATE_FILTERNUMS;
	}

}
