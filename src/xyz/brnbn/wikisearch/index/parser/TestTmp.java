package xyz.brnbn.wikisearch.index.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTmp {

	
	 public static void main(String[] args) {
		// TODO Auto-generated method stub
		String in = "The term /'/'[[wikt:anarchism|anarchism]]/'/' is a compound word composed from the word /'/'[[anarchy]]/'/' and the suffix ''[[-ism]]'',&lt;ref&gt;[http://www.etymonline.com/index.php?term=anarchism&amp;allowed_in_frame=0 Anarchism], [[Online etymology dictionary]].&lt;/ref&gt; themselves derived respectively from the Greek {{lang|grc|ἀναρχία}}, i.e. ''anarchy''&lt;ref&gt;{{LSJ|a)narxi/a|ἀναρχία|ref}}.&lt;/ref&gt;&lt;ref&gt;[http://www.merriam-webster.com/dictionary/anarchy Anarchy], [[Merriam-Webster]] online.&lt;/ref&gt;&lt;ref&gt;[http://www.etymonline.com/index.php?term=anarchy&amp;allowed_in_frame=0 Anarchy], [[Online etymology dictionary]].&lt;/ref&gt; (from {{lang|grc|ἄναρχος}}, ''anarchos'', meaning &quot;one without rulers&quot;;&lt;ref&gt;{{LSJ|a)/narxos|ἄναρχος|ref}}.&lt;/ref&gt; from the [[privative]] prefix [[privative alpha|ἀν]]- (''an-'', i.e. &quot;without&quot;) and {{lang|grc|ἀρχός}}, ''archos'', i.e. &quot;leader&quot;, &quot;ruler&quot;;&lt;ref&gt;{{LSJ|a)rxo/s|ἀρχός|ref}}&lt;/ref&gt; (cf. ''[[archon]]'' or {{lang|grc|ἀρχή}}, ''arkhē'', i.e. &quot;authority&quot;, &quot;sovereignty&quot;, &quot;realm&quot;, &quot;magistracy&quot;)&lt;ref&gt;{{LSJ|a)rxh/|ἀρχή|ref}}.&lt;/ref&gt;) and the suffix {{lang|grc|-ισμός}} or {{lang|grc|-ισμα}} (''-ismos'', ''-isma'', from the verbal [[infinitive]] suffix -ίζειν, ''-izein'').&lt;ref&gt;[http://www.etymonline.com/index.php?term=-ism&amp;allowed_in_frame=0 -ism], [[Online etymology dictionary]].&lt;/ref&gt; The first known use of this word was in 1539.&lt;ref&gt;&quot;Origin of ANARCHY";

		Pattern p = Pattern.compile("\\[\\[(.*?)\\]\\]");
		Matcher m = p.matcher(in);

		while(m.find()) {
		    System.out.println(m.group(1));
		}
	}
}
