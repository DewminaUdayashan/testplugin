#import "TestpluginPlugin.h"
#if __has_include(<testplugin/testplugin-Swift.h>)
#import <testplugin/testplugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "testplugin-Swift.h"
#endif

@implementation TestpluginPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftTestpluginPlugin registerWithRegistrar:registrar];
}
@end
