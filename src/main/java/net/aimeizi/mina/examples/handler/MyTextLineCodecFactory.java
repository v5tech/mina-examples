package net.aimeizi.mina.examples.handler;

import java.nio.charset.Charset;

import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

/**
 * 自定义字符编码解码类
 * @author welcome
 *
 */
public class MyTextLineCodecFactory extends TextLineCodecFactory {
	
	private static Charset charset = Charset.forName("gbk");

	public MyTextLineCodecFactory() {
		this(charset);
	}

	public MyTextLineCodecFactory(Charset charset) {
		super(charset);
	}
}
