import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataProfilingFlights {
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage: DataProfilingFlights <input path> <output path>");
            System.exit(-1);
        }

        String inputPath = args[0];
        String outputPath = args[1];
        Job job = new Job();
        job.setJarByClass(DataProfilingFlights.class);
        job.setJobName("DataProfilingFlights");

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.setMapperClass(DelayMapper.class);
        job.setReducerClass(SumReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.waitForCompletion(true);
        System.exit(0);
    }
}