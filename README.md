# Data-Pipeline-wtih-Kafka_Spark_Hadoop_Prefect

![Project Structure](/images/Project-Structure.png "a title")

![Data Pipeline](/images/Data-Pipeline.png "a title")

As it is almost clear from the images what the purpose of this project is, I would like to explain how I acheived the results and what were the processes involved.

Step 1: I used Python to extract data from APIs (these APIs are open). The data is related to cryptocurrency prices in INR and USD for 15-20 years. 
Step 2: After extracting the data I needed to create a dataframe so that I could save the data for further processing. I used PySpark to create the DataFrame. 
Step 3: To orchestrate the workflow I used Prefect(preferred due to it's interactive UI and plugins with major data warehouses). 
Step 4: To stage the dataframe I used Apache Hadoop and stored the data in parquet format to HDFS. 
Step 5: After staging it is time to aplly some transformations and cleaning. For this I again used Apache Spark as it is much faster and interactive because of it's UI and DAG flow. 
Step 6: After applying the required transformations it is time to save the data to a Data Warehouse. But before that I needed to verify that if the schema of my data is as per the requirement or is something out of place. 
Step 7: For schema verification I used schema registry with Kafka. Kakfa provides producer and consumer which keeps data up to date and prevent it from losing. This schema registry with Kafka helps to keep the values in desired format and prevents any data loss. For this I switched to JAVA as Kafka is more compatiblle with JAVA in comparison to Python
Step 8: After schema verification all the data is stored to a data warehouse(at first I used Snowflake and later I used Postgers with Docker Compose as Snowflake trial ended).

I believe these steps will be useful if anyone wants to interact and understand the working of this project.
