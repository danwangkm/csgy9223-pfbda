import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class DelayMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        String[] strArr = line.split(",");
        if (Double.parseDouble(strArr[15]) != 1) {
            String airport = strArr[8];
            int index = 0;
            if (Double.parseDouble(strArr[12]) > 15.0)
                index = 1;
            context.write(new Text(airport), new IntWritable(index));
        }
    }
}

