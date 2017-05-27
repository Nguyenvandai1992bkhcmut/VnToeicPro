# TOP_PATH refers to the project root dir (MyProject)
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE            := sqlite3-a
LOCAL_MODULE_FILENAME   := libsqlite3
LOCAL_SRC_FILES         := ../build/sqlite3.c
LOCAL_C_INCLUDES        := ../build
LOCAL_EXPORT_C_INCLUDES := ../build
LOCAL_CFLAGS            := -DSQLITE_THREADSAFE=1
include $(BUILD_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE            := sqlite3-static-cli
LOCAL_MODULE_FILENAME   := sqlite3-static
LOCAL_STATIC_LIBRARIES  := libsqlite3-a
LOCAL_SRC_FILES         := ../build/shell.c ../build/sqlite3.c
LOCAL_C_INCLUDES        := ../build
LOCAL_EXPORT_C_INCLUDES := ../build
LOCAL_CFLAGS            := -DSQLITE_THREADSAFE=1 -fPIE
LOCAL_LDFLAGS           := -fPIE -pie
include $(BUILD_EXECUTABLE)

# Build library 1
include $(CLEAR_VARS)
LOCAL_MODULE := bbb
LOCAL_SRC_FILES := model.cpp
include $(BUILD_SHARED_LIBRARY)

# Build library 2
include $(CLEAR_VARS)
LOCAL_MODULE := xyz
LOCAL_SRC_FILES := Padding_Type.cpp
include $(BUILD_SHARED_LIBRARY)


# Build library 3
include $(CLEAR_VARS)
LOCAL_MODULE := abc
LOCAL_SHARED_LIBRARIES := xyz
LOCAL_SRC_FILES := S_Box.cpp
include $(BUILD_SHARED_LIBRARY)

# Build library 4
include $(CLEAR_VARS)
LOCAL_MODULE := asd
LOCAL_CPP_FEATURES += exceptions
LOCAL_CPPFLAGS += -fexceptions
LOCAL_SHARED_LIBRARIES := abc
LOCAL_SHARED_LIBRARIES += xyz
LOCAL_SRC_FILES := Byte_Block.cpp
include $(BUILD_SHARED_LIBRARY)

# Build library 5
include $(CLEAR_VARS)
LOCAL_CPP_FEATURES += exceptions
LOCAL_CPPFLAGS += -fexceptions
LOCAL_MODULE := aaa
LOCAL_SHARED_LIBRARIES := abc
LOCAL_SHARED_LIBRARIES += xyz
LOCAL_SHARED_LIBRARIES += asd
LOCAL_SHARED_LIBRARIES += bbb
LOCAL_SRC_FILES := AES256_Base.cpp
include $(BUILD_SHARED_LIBRARY)

# Build library 6
include $(CLEAR_VARS)
LOCAL_STATIC_LIBRARIES := sqlite3-a
LOCAL_SHARED_LIBRARIES := aaa
LOCAL_SHARED_LIBRARIES += bbb
LOCAL_SHARED_LIBRARIES += ccc
LOCAL_MODULE := toeic1
LOCAL_SRC_FILES := SqliteDictionary.cpp
include $(BUILD_SHARED_LIBRARY)

# Build library 6
include $(CLEAR_VARS)
LOCAL_STATIC_LIBRARIES := sqlite3-a
LOCAL_SHARED_LIBRARIES := aaa
LOCAL_SHARED_LIBRARIES += bbb
LOCAL_SHARED_LIBRARIES += ccc
LOCAL_MODULE := toeic2
LOCAL_SRC_FILES := SqliteWord.cpp
include $(BUILD_SHARED_LIBRARY)


# Build library 6
include $(CLEAR_VARS)
LOCAL_STATIC_LIBRARIES := sqlite3-a
LOCAL_SHARED_LIBRARIES := aaa
LOCAL_SHARED_LIBRARIES += asd
LOCAL_MODULE := ccc
LOCAL_SRC_FILES := edcode.cpp
include $(BUILD_SHARED_LIBRARY)

# Build library 7
include $(CLEAR_VARS)
LOCAL_STATIC_LIBRARIES := sqlite3-a
LOCAL_SHARED_LIBRARIES := aaa
LOCAL_SHARED_LIBRARIES += toeic1
LOCAL_SHARED_LIBRARIES += bbb
LOCAL_SHARED_LIBRARIES += ccc
LOCAL_MODULE := jnixyz
LOCAL_SRC_FILES := jnidictionry.cpp
include $(BUILD_SHARED_LIBRARY)

# Build library 8
include $(CLEAR_VARS)
LOCAL_STATIC_LIBRARIES := sqlite3-a
LOCAL_SHARED_LIBRARIES := aaa
LOCAL_SHARED_LIBRARIES += toeic2
LOCAL_SHARED_LIBRARIES += bbb
LOCAL_SHARED_LIBRARIES += ccc
LOCAL_MODULE := jnizxy
LOCAL_SRC_FILES := jnivocabulary.cpp
include $(BUILD_SHARED_LIBRARY)


