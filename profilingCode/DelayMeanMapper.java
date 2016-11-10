import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class DelayMeanMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        String[] strArr = line.split(",");
        String departure = strArr[0];
        int data = (int) Math.round(Double.parseDouble(strArr[15]));
        context.write(new Text(departure), new IntWritable(data));
    }
}

