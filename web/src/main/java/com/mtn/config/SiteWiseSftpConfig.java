package com.mtn.config;

import com.jcraft.jsch.ChannelSftp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.messaging.MessageHandler;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class SiteWiseSftpConfig {

	@Value("${site-wise-sftp.host}")
	private String sftpHost;

	@Value("${site-wise-sftp.port}")
	private Integer sftpPort;

	@Value("${site-wise-sftp.username}")
	private String sftpUser;

	@Value("${site-wise-sftp.password}")
	private String sftpPasword;

	@Value("${site-wise-sftp.directory:/}")
	private String sftpRemoteDirectory;

	@Bean
	public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
		DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
		factory.setHost(sftpHost);
		factory.setPort(sftpPort);
		factory.setUser(sftpUser);
		factory.setPassword(sftpPasword);
		factory.setAllowUnknownKeys(true);
		return new CachingSessionFactory<>(factory);
	}

	@Bean
	@ServiceActivator(inputChannel = "toSftpChannel")
	public MessageHandler handler() {
		SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
		handler.setRemoteDirectoryExpression(new LiteralExpression(sftpRemoteDirectory));
		handler.setFileNameGenerator(message -> {
			DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
			String date = formatter.format(LocalDateTime.now().toLocalDate());
			return date + " MTN DB Extraction.csv";
		});
		return handler;
	}

	@MessagingGateway
	public interface SiteWiseSftpGateway {

		@Gateway(requestChannel = "toSftpChannel")
		void sendToSftp(File file);

	}

}
