package demo.processor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import demo.annotations.MyAnnotation;

@SupportedAnnotationTypes({"demo.annotations.MyAnnotation"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyProcessor extends AbstractProcessor  {
	
	@Override
	public boolean process(Set<? extends TypeElement> arg0, RoundEnvironment arg1) {
		int cnt = 0;
		System.out.print("start annotation process. ");
		for(Element annotatedElement: arg1.getElementsAnnotatedWith(MyAnnotation.class)) {
			System.out.println("annotations : " + cnt);
			generateCode(this.processingEnv.getFiler()); 
		}
		// TODO Auto-generated method stub
		return true;
	}
	
	private static void generateCode(Filer filer) {
		MethodSpec main = MethodSpec.methodBuilder("main")
			    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
			    .returns(void.class)
			    .addParameter(String[].class, "args")
			    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
			    .build();

			TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
			    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
			    .addMethod(main)
			    .build();
			
		String packageName = "demo.generatedCode";
		try {
			JavaFile.builder(packageName, helloWorld).build().writeTo(filer);
		} catch (IOException e) {
			System.out.print("Unable to write java file.");
			e.printStackTrace();
		}
	}
}
