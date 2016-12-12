import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataProfilingAirports {
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage: DataProfilingAirports <input path> <output path>");
            System.exit(-1);
        }

        String[] col = {"depMax", "depAvg", "arrMax", "arrAvg"};
        Class mappers[] = {DepMapper.class, DepMapper.class, ArrMapper.class, ArrMapper.class};
        Class reducers[] = {MaxReducer.class, MeanReducer.class, MaxReducer.class, MeanReducer.class};



        for (int i=0; i<col.length; i++) {
            String inputPath = args[0];
            String outputPath = args[1]+col[i];
            Job job = new Job();
            job.setJarByClass(DataProfilingAirports.class);
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