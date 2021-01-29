package pers.mine.scratchpad.base.nashorn;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Link;
import org.commonmark.renderer.html.AttributeProvider;
import org.junit.Before;
import org.junit.Test;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;

/**
 * Nashorn js引擎
 * 
 * @author Mine
 */
public class NashornTest {
	String md = "";
	String lute = "";

	@Before
	public void init() throws IOException {
		StringBuffer mdSbu = new StringBuffer();
		StringBuffer luteSbu = new StringBuffer();
		Files.lines(Paths.get("D:\\iWork\\vsc\\vditor\\src\\js\\lute\\lute.min.js"), StandardCharsets.UTF_8)
				.forEach((line) -> luteSbu.append(line).append("\n"));
		Files.lines(Paths.get("D:\\iWork\\vsc\\vditor\\demo\\test.md"), StandardCharsets.UTF_8)
				.forEach((line) -> mdSbu.append(line).append("\n"));

		md = mdSbu.toString();
		lute = luteSbu.toString();
	}

	@Test
	public void test() throws ScriptException, IOException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

		// final CompiledScript compiled = ((Compilable)engine).compile(scriptFile);

		Bindings one = engine.createBindings();
		one.put("md", md);
		engine.setBindings(one, ScriptContext.ENGINE_SCOPE);
		engine.eval(lute);
		String js = "var html = Lute.New().MarkdownStr('',md); print(html[0]||html[1]);";
		engine.eval(js);

	}

	@Test
	public void md2HtmlFlex() {
		DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(true, Extensions.ALL);
		Parser parser = Parser.builder(OPTIONS).build();
		HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();
		Node document = parser.parse(md);
		System.out.println(renderer.render(document));
	}
	@Test
	public void md2HtmlCommon1() {
		System.out.println(Arrays.toString("1.2".split("\\.")));
	}
	@Test
	public void md2HtmlCommon() {
		List<Extension> extensions = Arrays.asList(TablesExtension.create());
		org.commonmark.parser.Parser parser = org.commonmark.parser.Parser.builder().extensions(extensions).build();
		org.commonmark.node.Node document = parser.parse(md);
		org.commonmark.renderer.html.HtmlRenderer renderer = org.commonmark.renderer.html.HtmlRenderer.builder()
				.attributeProviderFactory(context -> new LinkAttributeProvider()).extensions(extensions).build();

		String content = renderer.render(document);
		System.out.println(content);
	}

	static class LinkAttributeProvider implements AttributeProvider {
		@Override
		public void setAttributes(org.commonmark.node.Node node, String tagName, Map<String, String> attributes) {
			if (node instanceof Link) {
				attributes.put("target", "_blank");
			}
			if (node instanceof org.commonmark.node.Image) {
				attributes.put("title", attributes.get("alt"));
			}
		}
	}

}
