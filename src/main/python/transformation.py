from pyspark.sql import SparkSession
from pyspark.sql.types import DoubleType, DateType


class TransformDF:

    def read_from_hdfs(self, path):
        spark = (
            SparkSession.builder.master("yarn")
            .appName("Spark-read-DF")
            .getOrCreate()
        )
        df = spark.read.format("parquet").option("header", "true").load("hdfs://localhost:9000/BTC/")
        return df
    
    def change_to_proper_names(self, df):
        for column in df.columns:
            alternate = input(f"Enter another name for {column}")
            df = df.withColumnRenamed(column, alternate)

        return df
    
    def change_to_proper_dtypes(self, df):
        for column in df.columns:
            if isinstance(column, str):
                if column != 'Date':
                    df = df.withColumn(column, df[column].cast(DoubleType()))
                else:
                    df = df.withColumn(column, df[column].cast(DateType()))
        return df

    def stage_to_hdfs(self, df, path):
        try:
            df.write.save(path, format='parquet', mode='append')
            return 'Success'
        except Exception as e:
            print(e)
        finally:
            return

obj = TransformDF()
df = obj.read_from_hdfs()
df2 = obj.change_to_proper_names(df)
df3 = obj.change_to_proper_dtypes(df2)
obj.stage_to_hdfs(df3, '')
