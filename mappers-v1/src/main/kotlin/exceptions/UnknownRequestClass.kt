package exceptions

class UnknownRequestClass(clazz: Class<*>, context: Class<*>): RuntimeException("Class $clazz cannot be mapped to $context")