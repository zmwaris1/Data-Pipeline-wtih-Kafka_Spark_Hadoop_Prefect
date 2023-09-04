import requests
import pandas as pd
from pyspark.sql import SparkSession
from prefect import task


class DataFromAPI:
    def flatten(self, dct, separator="_"):
        """A fast way to flatten a dictionary,"""
        res = {}
        queue = [("", dct)]

        while queue:
            prefix, d = queue.pop()
            for k, v in d.items():
                key = prefix + k
                if not isinstance(v, dict):
                    res[key] = v
                else:
                    queue.append((key + separator, v))

        return res

    def __get_data(self, url: str) -> None:
        if not url:
            return "Please enter a valid url."
        response = requests.get(url)
        data = response.json()
        for k, v in data["Time Series (Digital Currency Daily)"].items():
            d = {"date": k, "": v}
            rec = self.flatten(d)
            yield rec

    def get_data(self, url):
        return self.__get_data(url)

    def __create_dataframe_from_records(self, rec):
        df = pd.DataFrame.from_dict(rec)
        return df

    def create_final_df(self, rec):
        return self.__create_dataframe_from_records(rec)

    def __create_spark_df(self, df):
        spark = (
            SparkSession.builder.master("local[*]")
            .appName("Spark-DF")
            .getOrCreate()
        )
        spark_df = spark.createDataFrame(df)
        spark_df.createOrReplaceTempView("spark_df")
        return spark_df

    def create_spark_df(self, df):
        return self.__create_spark_df(df)

    def __save_df(self, df, path):
        try:
            df.write.save(path, format='parquet', mode='append')
            return 'Success'
        except Exception as e:
            print(e)
        finally:
            return

    def save_df(self, df, path):
        return self.__save_df(df, path)