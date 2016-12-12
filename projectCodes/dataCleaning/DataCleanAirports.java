import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataCleanAirports {
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage: DataCleanAirports <input path> <output path>");
            System.exit(-1);
        }

        String inputPath = args[0];
        String outputPath = args[1];
        Job job = new Job();
        job.setJarByClass(DataCleanAirports.class);
        job.setJobName("DataCleanAirports");

        FileInputFormat.addInputPath(job, new Path(inputPath));
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.setMapperClass(AirportMapper.class);
        job.setReducerClass(NormalReducer.class);

        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);

        job.waitForCompletion(true);
        System.exit(0);
    }
}