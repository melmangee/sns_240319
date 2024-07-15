package com.sns.timeline;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TimelineController {

	@GetMapping("/timeline/timeline-view")
	public String timelineView() {
		return "timeline/timeline";
	}
}
