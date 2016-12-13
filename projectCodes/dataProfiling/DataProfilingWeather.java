import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataProfilingWeather {
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage: DataProfilingWeather <input path> <output path>");
            System.exit(-1);
        }

        String[] col = {"tempMax", "tempMin"};
        Class mappers[] = {MaxTempMapper.class, MinTempMapper.class};
        Class reducers[] = {MeanReducer.class, MeanReducer.class};



        for (int i=0; i<col.length; i++) {
            String inputPath = args[0];
            String outputPath = args[1]+col[i];
            Job job = new Job();
            job.setJarByClass(DataProfilingWeather.class);
            job.setJobName(col[i]);

            FileInputFormat.addInputPath(job, new Path(inputPath));
            FileOutputFormat.setOutputPath(job, new Path(outputPath));

            job.setMapperClass(mappers[i]);
            job.setReducerClass(reducers[i]);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            job.waitForCompletion(true);
        }

        System.exit(0);
    }
}