import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class DataProfiling {
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage: DataProfiling <input path> <output path>");
            System.exit(-1);
        }

        String[] col = {"ontime", "late15", "late30", "late45", "cancelled", "diverted", "delayObservations", "delayMean"};
        Class mappers[] = {OnTimeMapper.class, Late15Mapper.class, Late30Mapper.class, Late45Mapper.class, CancelledMapper.class, DivertedMapper.class, DelayObservationsMapper.class, DelayMeanMapper.class};
        Class reducers[] = {SumReducer.class, SumReducer.class, SumReducer.class, SumReducer.class, SumReducer.class, SumReducer.class, SumReducer.class, MeanReducer.class};



        for (int i=0; i<col.length; i++) {
            String inputPath = args[0];
            String outputPath = args[1]+"/"+col[i];
            Job job = new Job();
            job.setJarByClass(DataProfiling.class);
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