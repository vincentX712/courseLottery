import pandas as pd
import mysql.connector

mysql_config = {
    'host': '192.168.17.128',
    'user': 'root',
    'password': '123',
    'database': 'lottery'
}

conn = mysql.connector.connect(**mysql_config)
cursor = conn.cursor()


def insert_data(table_name, columns: list[str], data, params_num):
    # insert_query = "insert into " + table_name + " values (" + ",".join(["%s"] * params_num) + ")"
    insert_query = f'INSERT INTO {table_name} ( {", ".join(columns)} ) VALUES ({", ".join(["%s"] * params_num)})'
    print(insert_query)
    cursor.executemany(insert_query, data)
    conn.commit()


def read_excel_and_insert_into_mysql(excel_file, sheet_name, param_num, table_name, columns: list[str]):
    df = pd.read_excel(excel_file, sheet_name=sheet_name, header=0)
    data = [tuple(row) for row in df.values]
    # 将空值替换为None
    data = [tuple(None if pd.isna(value) else value for value in row) for row in data]
    #print(data)
    insert_data(table_name, columns, data, param_num)


if __name__ == '__main__':
    excel_file_path = 'data.xlsx'

    read_excel_and_insert_into_mysql(excel_file_path, 'experts', 1, 't_expert', ["name"])
    read_excel_and_insert_into_mysql(excel_file_path, 'teachers', 5, 't_teacher', ["id","name", "title", "education", "age"])
    read_excel_and_insert_into_mysql(excel_file_path, 'courses', 11, 't_course', ["date", "week", "node_id", "node_name", "lesson", "major", "teacher_id", "campus_id", "campus_name", "classroom", "notes"])

    cursor.close()
    conn.close()
