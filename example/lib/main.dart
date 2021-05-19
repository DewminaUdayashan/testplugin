import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:testplugin/testplugin.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
Stream? stream;
  @override
  void initState() {
    super.initState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    Testplugin testplugin = Testplugin();
    try {

           testplugin.trackLocation().then((value) {
             setState(() {
               stream=value;
             });
           });
    } catch (e) {
      print(e);
    }

    if (!mounted) return;

  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: Colors.blue,
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            initPlatformState();

          },
        ),
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: StreamBuilder(
          stream: stream,
          builder: (context, snapshot) {
            if(snapshot.hasData){
              return Center(
                child: Text('Running on: ${snapshot.data.toString()}'),
              );
            }
            return Center(
              child: Text('Running on: $_platformVersion\n'),
            );
          }
        ),
      ),
    );
  }
}
