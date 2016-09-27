import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PageRankMapper extends Mapper<LongWritable, Text, Text, Text> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        String[] strArr = line.split(" ");
        String currentPage = strArr[0];
        double pr = Double.parseDouble(strArr[strArr.length-1]);
        int outlinkCount = strArr.length - 2;
        String outlinks = "";
        for (int i=1; i<strArr.length-1; i++) {
            outlinks = outlinks + strArr[i] + " ";
            context.write(new Text(strArr[i]), new Text(currentPage+" "+(pr/outlinkCount)));
        }

        context.write(new Text(currentPage), new Text(outlinks.trim()));
    }
}

