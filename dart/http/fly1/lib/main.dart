// ignore_for_file: avoid_print

import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget {
  const MyHomePage({super.key});

  void _getBut1ON() {
    http
        .get(Uri.parse('https://iocontrol.ru/api/sendData/mob_fly1/but1/1'))
        .then((response) {
      print("Response status: ${response.statusCode}");
      print("Response body: ${response.body}");
    }).catchError((error) {
      print("Error: $error");
    });
  }

  void _getBut1OFF() {
    // <----- Step 2
    http
        .get(Uri.parse('https://iocontrol.ru/api/sendData/mob_fly1/but1/0'))
        .then((response) {
      print("Response status: ${response.statusCode}");
      print("Response body: ${response.body}");
    }).catchError((error) {
      print("Error: $error");
    });
  }

  void _post() {
    var url = Uri.parse('https://jsonplaceholder.typicode.com/posts');
    var data = {'title': 'foo', 'body': 'bar', 'userId': 1};
    var body = jsonEncode(data);
    http.post(
      url,
      headers: {"Content-Type": "application/json"},
      body: body,
    ).then((response) {
      print("Response status: ${response.statusCode}");
      print("Response body: ${response.body}");
    }).catchError((error) {
      print("Error: $error");
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Theme.of(context).colorScheme.inversePrimary,
          title: const Text("http"),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              TextButton(
                style: ButtonStyle(
                  foregroundColor:
                      MaterialStateProperty.all<Color>(Colors.blue),
                ),
                onPressed: _getBut1ON,
                child: const Text('ON1'),
              ),
              TextButton(
                style: ButtonStyle(
                  foregroundColor:
                      MaterialStateProperty.all<Color>(Colors.blue),
                ),
                onPressed: _getBut1OFF,
                child: const Text('OFF1'),
              ),
              TextButton(
                style: ButtonStyle(
                  foregroundColor:
                      MaterialStateProperty.all<Color>(Colors.blue),
                ),
                onPressed: _post,
                child: const Text('POST'),
              ),
            ],
          ),
        ));
  }
}
