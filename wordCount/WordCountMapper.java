import java.io.IOException;
import org.apache.hadoop.io.IntWritable; 
import org.apache.hadoop.io.LongWritable; 
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String line = value.toString();
		
		String[] words = {"hackathon", "Dec", "Chicago", "Java"};
        for (int i=0; i<words.length; i++) {
            int count = 0;
            if (line.toLowerCase().indexOf(words[i].toLowerCase()) > -1) {
                count = 1;
            }
            context.write(new Text(words[i]), new IntWritable(count));
        }
	}
}

