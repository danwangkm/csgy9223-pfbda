import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class AirportMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] strArr = line.split(",");
        String airport = strArr[0];
        strArr[0] = "\"" + airport + "\"";
        String[] date = strArr[1].split("/");
        String month = String.format("%02d", Integer.parseInt(date[0]));
        String day = String.format("%02d", Integer.parseInt(date[1]));
        String year = "20" + date[2];

        strArr[1] = year + "-" + month + "-" + day;
        String output = "";
        for (String str : strArr) {
            output = output + ","+str;
        }
        context.write(key, new Text(output.substring(1)));
    }
}

