# Data-Pipeline-wtih-Kafka_Spark_Hadoop_Prefect

![Project Structure](/images/Project-Structure.png "a title")

![Data Pipeline](/images/Data-Pipeline.png "a title")

As it is almost clear from the images what the purpose of this project is, I would like to explain how I acheived the results and what were the processes involved.

<ul>
<li><b>Step 1:</b> I used Python to extract data from APIs (these APIs are open). The data is related to cryptocurrency prices in INR and USD for 15-20 years.</li>
<li><b>Step 2:</b> After extracting the data I needed to create a dataframe so that I could save the data for further processing. I used PySpark to create the DataFrame.</li>
<li><b>Step 3:</b> To orchestrate the workflow I used Prefect(preferred due to it's interactive UI and plugins with major data warehouses).</li>
<li><b>Step 4:</b> To stage the dataframe I used Apache Hadoop and stored the data in parquet format to HDFS.</li>
<li><b>Step 5:</b> After staging it is time to aplly some transformations and cleaning. For this I again used Apache Spark as it is much faster and interactive because of it's UI and DAG flow.</li>
<li><b>Step 6:</b> After applying the required transformations it is time to save the data to a Data Warehouse. But before that I needed to verify that if the schema of my data is as per the requirement or is something out of place.</li>
<li><b>Step 7:</b> For schema verification I used schema registry with Kafka. Kakfa provides producer and consumer which keeps data up to date and prevent it from losing. This schema registry with Kafka helps to keep the values in desired format and prevents any data loss. For this I switched to JAVA as Kafka is more compatiblle with JAVA in comparison to Python.</li>
<li><b>Step 8:</b> After schema verification all the data is stored to a data warehouse(at first I used Snowflake and later I used Postgers with Docker Compose as Snowflake trial ended).</li>
</ul>

I believe these steps will be useful if anyone wants to interact and understand the working of this project.
