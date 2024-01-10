// http://students.yss.su/adminer/
// компилируем строго: flutter run

// ignore_for_file: avoid_print

import 'dart:async';
import 'package:mysql1/mysql1.dart';
//import 'dart:io';
import 'package:flutter/material.dart';

// изменить на свой
var tableName = 'Terenteva';

Future createTable() async {
  final conn = await MySqlConnection.connect(ConnectionSettings(
      host: 'students.yss.su',
      port: 3306,
      user: 'iu9mobile',
      db: 'iu9mobile',
      password: 'bmstubmstu123'));

  await conn.query(
      'CREATE TABLE IF NOT EXISTS $tableName(name char(100), email char(100), age int)');
  await conn.close();
}

Future insertData(name, email, age) async {
  // Open a connection (testdb should already exist)
  final conn = await MySqlConnection.connect(ConnectionSettings(
      host: 'students.yss.su',
      port: 3306,
      user: 'iu9mobile',
      db: 'iu9mobile',
      password: 'bmstubmstu123'));

  // Insert some data
  await conn.query(
      'insert into $tableName (name, email, age) values (?, ?, ?)',
      [name, email, age]);

  // Finally, close the connection
  await conn.close();
}

Future<String> getData() async {
  String all = "";
  // Open a connection (testdb should already exist)
  final conn = await MySqlConnection.connect(ConnectionSettings(
      host: 'students.yss.su',
      port: 3306,
      user: 'iu9mobile',
      db: 'iu9mobile',
      password: 'bmstubmstu123'));

  // Insert some data
  var result = await conn.query(
      'select * from Terenteva');

  for (var row in result) {
    all = "$all\n$row";
  }
  print(all);
  // Finally, close the connection
  await conn.close();
  return all;
}

class MyForm extends StatefulWidget {
  const MyForm({super.key});

  @override
  State<StatefulWidget> createState() => MyFormState();
}

class MyFormState extends State {
  final _formKey = GlobalKey<FormState>();
  String _body = "";
  String name = "", email = "";
  int age = 0;

  @override
  Widget build(BuildContext context) {
    return Container(
        padding: const EdgeInsets.all(10.0),
        child: Form(
            key: _formKey,
            child: Column(
              children: <Widget>[
                const Text(
                  'Имя:',
                  style: TextStyle(fontSize: 20.0),
                ),
                TextFormField(validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Тестовое поле - не заполнено!';
                  } else {
                    print('name---->$value');
                    name = value;
                  }
                }),
                const Text(
                  'Почта:',
                  style: TextStyle(fontSize: 20.0),
                ),
                TextFormField(validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Тестовое поле - не заполнено!';
                  } else {
                    print('email---->$value');
                    email = value;
                  }
                }),
                const Text(
                  'Возраст:',
                  style: TextStyle(fontSize: 20.0),
                ),
                TextFormField(validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Тестовое поле - не заполнено!';
                  } else {
                    print('age---->$value');
                    age = int.parse(value);
                  }
                }),
                const SizedBox(height: 20.0),
                ElevatedButton(
                  onPressed: () async {
                    if (_formKey.currentState!.validate()) {
                      ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
                        content: Text('Форма заполнена!'),
                        backgroundColor: Colors.red,
                      ));
                    }
                    await insertData(name, email, age);
                    _body = await getData();
                    setState(() {
                    });
                  },
                  style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.purple,
                      padding: const EdgeInsets.symmetric(
                          horizontal: 50, vertical: 20),
                      textStyle: const TextStyle(
                          fontSize: 30, fontWeight: FontWeight.bold)),
                  child: const Text('send'),
                ),
                Text(_body),
              ],
            )));
  }
}

Future main() async {
  createTable();

  return runApp(MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
          appBar: AppBar(title: const Text('IU9 - Форма ввода')),
          body: const MyForm())));
}
