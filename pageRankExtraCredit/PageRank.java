import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageRank {
    public static void main(String[] args) throws Exception {

        final int ITERATOR_COUNT = 3;

        if (args.length != 2) {
            System.err.println("Usage: PageRank <input path> <output path>");
            System.exit(-1);
        }

        int i = 0;
        String inputPath = args[0];
        String outputPath = args[1]+i;

        while (i < ITERATOR_COUNT) {
            Job job = new Job();
            job.setJarByClass(PageRank.class);
            job.setJobName("PageRank");

            FileInputFormat.addInputPath(job, new Path(inputPath));
            FileOutputFormat.setOutputPath(job, new Path(outputPath));

            job.setMapperClass(PageRankMapper.class);
            job.setReducerClass(PageRankReducer.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            job.waitForCompletion(true);

            inputPath = args[1]+i;
            i++;
            if (i == ITERATOR_COUNT - 1)
                outputPath = args[1];
            else
                outputPath = args[1]+i;
        }

        System.exit(0);
    }
}