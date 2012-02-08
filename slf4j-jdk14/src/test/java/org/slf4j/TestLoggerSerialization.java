package org.slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import junit.framework.Assert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLoggerSerialization extends junit.framework.TestCase {

	static class LoggerHolder implements Serializable {
		private static final long serialVersionUID = 1L;

		private Logger log = LoggerFactory.getLogger(LoggerHolder.class);

		public String toString() {
			return "log=" + getLog();
		}

		public Logger getLog() {
			return log;
		}
	}

	public void testLoggerSerializable() throws IOException,
			ClassNotFoundException {

		LoggerHolder lh1 = new LoggerHolder();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(baos);
		out.writeObject(lh1);
		out.close();

		lh1 = null;

		byte[] serializedLoggerHolder = baos.toByteArray();

		InputStream is = new ByteArrayInputStream(serializedLoggerHolder);
		ObjectInputStream in = new ObjectInputStream(is);
		LoggerHolder lh2 = (LoggerHolder) in.readObject();

		Assert.assertNotNull(lh2);
		Assert.assertNotNull(lh2.getLog());
		lh2.getLog().info("You must see this message as a log message");
	}
}
