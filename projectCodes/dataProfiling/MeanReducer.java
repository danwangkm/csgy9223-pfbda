import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MeanReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int sum = 0;
        int count = 0;
        for (IntWritable value : values) {
            sum = sum + value.get();
            count ++;
        }

        context.write(key, new IntWritable((int)Math.round((double)sum/count)));

    }
}