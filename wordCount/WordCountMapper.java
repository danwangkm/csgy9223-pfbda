import java.io.IOException;
import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.util.HashMap;
import java.util.Map;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		
		HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("hackathon", 0);
        result.put("Dec", 0);
        result.put("Chicago", 0);
        result.put("Java", 0);
		wordCount("hackathon", line, result);
        wordCount("Dec", line, result);
        wordCount("Chicago", line, result);
        wordCount("Java", line, result);

        for (Map.Entry<String, Integer> entry : result.entrySet()) {
        	context.write(new Text(entry.getKey()), new IntWritable(entry.getValue()));
        }
	}

	public static void wordCount(String word, String line, HashMap<String, Integer> result) {

        if (line.toLowerCase().indexOf(word.toLowerCase()) > -1) {
            result.put(word, result.get(word)+1);
        }
    }
}

