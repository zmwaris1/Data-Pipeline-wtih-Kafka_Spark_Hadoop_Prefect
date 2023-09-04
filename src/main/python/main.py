from pathlib import Path
from dotenv import load_dotenv
import os
from data import DataFromAPI
from prefect import flow

dot_env = Path(
    "/media/zmwaris/mydrive/data-folder/Project-1/Data-Pipeline-wtih-Kafka_Spark_Hadoop_Prefect/secrets/.env"
)
secrets = load_dotenv(dotenv_path=dot_env)

ALPHAVANTAGE_BASE_URL = os.environ.get("ALPHAVANTAGE_BASE_URL")
ALPHAVANTAGE_API_KEY = os.environ.get("ALPHAVANTAGE_API_KEY")

obj_url = f"{ALPHAVANTAGE_BASE_URL}function=DIGITAL_CURRENCY_DAILY&symbol=BTC&market=INR&apikey={ALPHAVANTAGE_API_KEY}"

@flow(name="final_API_data", log_prints=True, retries=3)
def main():
    obj = DataFromAPI()
    rec = obj.get_data(obj_url)
    df = obj.create_final_df(rec)
    spark_df = obj.create_spark_df(df)
    print(spark_df.show())
    path = 'hdfs://localhost:9000/BTC/'
    obj.save_df(spark_df, path)


if __name__ == "__main__":
    main()
