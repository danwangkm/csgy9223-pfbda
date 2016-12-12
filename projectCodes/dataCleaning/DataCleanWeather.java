import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataCleanWeather {
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage: DataCleanWeather <input path> <output path>");
            System.exit(-1);
        }

        String inputPath = args[0];
        String outputPath = args[1];
        Job job = new Job();
        job.setJarByClass(DataCleanWeather.class);
        job.setJobName("DataCleanWeather");

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.setMapperClass(WeatherMapper.class);
        job.setReducerClass(NormalReducer.class);

        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);

        job.waitForCompletion(true);
        System.exit(0);
    }
}