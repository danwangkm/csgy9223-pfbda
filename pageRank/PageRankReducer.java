import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankReducer extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        double pr = 0;
        String outlinks = "";
        for (Text value : values) {
            String line = value.toString();
            String[] strArr = line.split("\\s+");
            if (strArr[strArr.length-1].indexOf(".") != -1) {
                pr += Double.parseDouble(strArr[strArr.length-1]);
            }
            else {
                outlinks = line;
            }
        }
        context.write(key, new Text(outlinks+" "+pr));

    }
}