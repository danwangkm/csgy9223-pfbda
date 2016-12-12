import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WeatherMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] strArr = line.split(",");
        String date = strArr[2];
        strArr[2] = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6);
        String output = "";
        for (String str : strArr) {
            output = output + ","+str;
        }
        //System.out.println(String.join(",", strArr));
        System.out.println(output);
        context.write(key, new Text(output.substring(1)));
    }
}

