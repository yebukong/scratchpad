package pers.mine.scratchpad.other.jsch;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SimpleJschTest {
	private String host;
	private String port;
	private String name;
	private String password;

	@Before
	public void init() {
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("输入连接信息，格式 -  host|port|user|password ");
			String nextLine = scan.nextLine();
			String[] split = nextLine.split("\\|");
			host = split[0];
			port = split[1];
			name = split[2];
			password = split[3];
		}
	}

	@Test
	public void sshExecTest() throws Exception {
		System.out.println(String.format("host - %s post - %s name - %s password - %s ", host, port, name, password));
		JSch jsch = new JSch();
		Session session = jsch.getSession(name, host, Integer.valueOf(port));
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		channel.setCommand("cd /;ls");
		channel.setInputStream(null);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		channel.setErrStream(os);
		InputStream is = channel.getInputStream();
		channel.connect();

		byte[] bytes = new byte[1024];
		while (true) {
			while (is.available() > 0) {
				int i = is.read(bytes, 0, bytes.length);
				if (i < 0) {
					break;
				}
				os.write(bytes, 0, i);
			}
			if (channel.isClosed()) {
				if (is.available() > 0) {
					continue;
				}
				break;
			}
			Thread.sleep(50L);
		}
		System.out.println(os.toString());
	}
}
