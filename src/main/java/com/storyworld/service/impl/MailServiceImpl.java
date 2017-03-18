package com.storyworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.storyworld.domain.sql.Mail;
import com.storyworld.enums.Status;
import com.storyworld.repository.sql.MailReposiotory;
import com.storyworld.service.MailService;

import freemarker.template.Configuration;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	MailReposiotory mailReposiotory;

	@Autowired
	private Configuration freemarkerConfiguration;

	private final String FROM = "storyworld@gamil.com";

	private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

	@Override
	public void sendEmail(Mail mail) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(mail.getEmail());
				message.setFrom(FROM);
				message.setSubject(mail.getTemplate());
				Map<String, Object> model = new HashMap<>();
				model.put("mail", mail);
				String text = geFreeMarkerTemplateContent(model, mail.getTemplate());
				message.setText(text, true);
			}
		};
		try {
			mailSender.send(preparator);
			mail.setStatus(Status.FINISHED);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			mail.setStatus(Status.ERROR);
		} finally {
			mailReposiotory.save(mail);
		}
	}

	private String geFreeMarkerTemplateContent(Map<String, Object> model, String template) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(FreeMarkerTemplateUtils
					.processTemplateIntoString(freemarkerConfiguration.getTemplate(template), model));
			return content.toString();
		} catch (Exception e) {
			LOG.error("Exception occured while processing fmtemplate:" + e.getMessage());
		}
		return "";
	}
}
