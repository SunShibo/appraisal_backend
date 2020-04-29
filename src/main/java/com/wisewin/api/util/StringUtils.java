package com.wisewin.api.util;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串操作工具类
 */
@SuppressWarnings("unchecked")
public class StringUtils {

	private static final Logger log = Logger.getLogger(StringUtils.class);

	private static final String FOLDER_SEPARATOR = "/";

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

	private static final String TOP_PATH = "..";

	private static final String CURRENT_PATH = ".";

	private static final char EXTENSION_SEPARATOR = '.';


	/**
	 * 过滤表情
	 * @param str
	 * @return
	 */
	public static String removeNonBmpUnicode(String str) {
		if (str == null) {
			return null;
		}
		str = str.replaceAll("[^\u0000-\uFFFF]", "");
		return str;
	}


	/**
	 * 校验字符串是否和非法字符
	 * @param stc
	 * @return
	 */
	public static boolean canshu(String stc){
		String regEx = "[ `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(stc);
		return m.find();
	}

	/**
	 * Check if a String has length.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength(&quot;&quot;) = false
	 * StringUtils.hasLength(&quot; &quot;) = true
	 * StringUtils.hasLength(&quot;Hello&quot;) = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be <code>null</code>
	 * @return <code>true</code> if the String is not null and has length
	 */
	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * Check if a String has text. More specifically, returns <code>true</code>
	 * if the string not <code>null<code>, it's <code>length is > 0</code>, and it has at least one
	 * non-whitespace character.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText(&quot;&quot;) = false
	 * StringUtils.hasText(&quot; &quot;) = false
	 * StringUtils.hasText(&quot;12345&quot;) = true
	 * StringUtils.hasText(&quot; 12345 &quot;) = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be <code>null</code>
	 * @return <code>true</code> if the String is not null, length > 0, and not
	 *         whitespace only
	 * @see Character#isWhitespace
	 */
	public static boolean hasText(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Trim leading and trailing whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see Character#isWhitespace
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		while (buf.length() > 0
				&& Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Trim leading whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see Character#isWhitespace
	 */
	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * Trim trailing whitespace from the given String.
	 * 
	 * @param str
	 *            the String to check
	 * @return the trimmed String
	 * @see Character#isWhitespace
	 */
	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0
				&& Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * SensitiveWordsUtil if the given String starts with the specified prefix, ignoring
	 * upper/lower case.
	 * 
	 * @param str
	 *            the String to check
	 * @param prefix
	 *            the prefix to look for
	 * @see String#startsWith
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	/**
	 * SensitiveWordsUtil if the given String ends with the specified suffix, ignoring
	 * upper/lower case.
	 * 
	 * @param str
	 *            the String to check
	 * @param suffix
	 *            the suffix to look for
	 * @see String#endsWith
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) {
			return false;
		}
		if (str.endsWith(suffix)) {
			return true;
		}
		if (str.length() < suffix.length()) {
			return false;
		}

		String lcStr = str.substring(str.length() - suffix.length())
				.toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}

	/**
	 * Count the occurrences of the substring in string s.
	 * 
	 * @param str
	 *            string to search in. Return 0 if this is null.
	 * @param sub
	 *            string to search for. Return 0 if this is null.
	 */
	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0
				|| sub.length() == 0) {
			return 0;
		}
		int count = 0, pos = 0, idx = 0;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	/**
	 * Replace all occurences of a substring within a string with another
	 * string.
	 * 
	 * @param inString
	 *            String to examine
	 * @param oldPattern
	 *            String to replace
	 * @param newPattern
	 *            String to insert
	 * @return a String with the replacements
	 */
	public static String replaceplus(String inString, String oldPattern,
									 String newPattern) {
		if (inString == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();
		// output StringBuffer we'll build up
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		// remember to append any characters to the right of a match
		return sbuf.toString();
	}

	/**
	 * Delete all occurrences of the given substring.
	 * 
	 * @param pattern
	 *            the pattern to delete all occurrences of
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	/**
	 * Delete any character in a given string.
	 * 
	 * @param charsToDelete
	 *            a set of characters to delete. E.g. "az\n" will delete 'a's,
	 *            'z's and new lines.
	 */
	public static String deleteAny(String inString, String charsToDelete) {
		if (inString == null || charsToDelete == null) {
			return inString;
		}
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				out.append(c);
			}
		}
		return out.toString();
	}

	// ---------------------------------------------------------------------
	// Convenience methods for working with formatted Strings
	// ---------------------------------------------------------------------

	/**
	 * Quote the given String with single quotes.
	 * 
	 * @param str
	 *            the input String (e.g. "myString")
	 * @return the quoted String (e.g. "'myString'"), or
	 *         <code>null<code> if the input was <code>null</code>
	 */
	public static String quote(String str) {
		return (str != null ? "'" + str + "'" : null);
	}

	/**
	 * Turn the given Object into a String with single quotes if it is a String;
	 * keeping the Object as-is else.
	 * 
	 * @param obj
	 *            the input Object (e.g. "myString")
	 * @return the quoted String (e.g. "'myString'"), or the input object as-is
	 *         if not a String
	 */
	public static Object quoteIfString(Object obj) {
		return (obj instanceof String ? quote((String) obj) : obj);
	}

	/**
	 * Unqualify a string qualified by a '.' dot character. For example,
	 * "this.name.is.qualified", returns "qualified".
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	/**
	 * Unqualify a string qualified by a separator character. For example,
	 * "this:name:is:qualified" returns "qualified" if using a ':' separator.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @param separator
	 *            the separator
	 */
	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName
				.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	/**
	 * Capitalize a <code>String</code>, changing the first letter to upper case
	 * as per {@link Character#toUpperCase(char)}. No other letters are changed.
	 * 
	 * @param str
	 *            the String to capitalize, may be <code>null</code>
	 * @return the capitalized String, <code>null</code> if null
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	/**
	 * Uncapitalize a <code>String</code>, changing the first letter to lower
	 * case as per {@link Character#toLowerCase(char)}. No other letters are
	 * changed.
	 * 
	 * @param str
	 *            the String to uncapitalize, may be <code>null</code>
	 * @return the uncapitalized String, <code>null</code> if null
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str,
												   boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}

	/**
	 * Extract the filename from the given path, e.g. "mypath/myfile.txt" ->
	 * "myfile.txt".
	 * 
	 * @param path
	 *            the file path (may be <code>null</code>)
	 * @return the extracted filename, or <code>null</code> if none
	 */
	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1)
				: path);
	}

	/**
	 * Extract the filename extension from the given path, e.g.
	 * "mypath/myfile.txt" -> "txt".
	 * 
	 * @param path
	 *            the file path (may be <code>null</code>)
	 * @return the extracted filename extension, or <code>null</code> if none
	 */
	public static String getFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(sepIndex + 1) : null);
	}

	/**
	 * Strip the filename extension from the given path, e.g.
	 * "mypath/myfile.txt" -> "mypath/myfile".
	 * 
	 * @param path
	 *            the file path (may be <code>null</code>)
	 * @return the path with stripped filename extension, or <code>null</code>
	 *         if none
	 */
	public static String stripFilenameExtension(String path) {
		if (path == null) {
			return null;
		}
		int sepIndex = path.lastIndexOf(EXTENSION_SEPARATOR);
		return (sepIndex != -1 ? path.substring(0, sepIndex) : path);
	}

	/**
	 * Apply the given relative path to the given path, assuming standard Java
	 * folder separation (i.e. "/" separators);
	 * 
	 * @param path
	 *            the path to start from (usually a full file path)
	 * @param relativePath
	 *            the relative path to apply (relative to the full file path
	 *            above)
	 * @return the full file path that results from applying the relative path
	 */
	public static String applyRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!relativePath.startsWith(FOLDER_SEPARATOR)) {
				newPath += FOLDER_SEPARATOR;
			}
			return newPath + relativePath;
		} else {
			return relativePath;
		}

	}

	/**
	 * Normalize the path by suppressing sequences like "path/.." and inner
	 * simple dots.
	 * <p>
	 * The result is convenient for path comparison. For other uses, notice that
	 * Windows separators ("\") are replaced by simple slashes.
	 * 
	 * @param path
	 *            the original path
	 * @return the normalized path
	 */
	public static String cleanPath(String path) {
		String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR,
				FOLDER_SEPARATOR);

		// Strip prefix from path to analyze, to not treat it as part of the
		// first path element. This is necessary to correctly parse paths like
		// "file:core/../core/io/Resource.class", where the ".." should just
		// strip the first "core" directory while keeping the "file:" prefix.
		int prefixIndex = pathToUse.indexOf(":");
		String prefix = "";
		if (prefixIndex != -1) {
			prefix = pathToUse.substring(0, prefixIndex + 1);
			pathToUse = pathToUse.substring(prefixIndex + 1);
		}

		String[] pathArray = delimitedListToStringArray(pathToUse,
				FOLDER_SEPARATOR);
		List pathElements = new LinkedList();
		int tops = 0;

		for (int i = pathArray.length - 1; i >= 0; i--) {
			if (CURRENT_PATH.equals(pathArray[i])) {
				// Points to current directory - drop it.
			} else if (TOP_PATH.equals(pathArray[i])) {
				// Registering top path found.
				tops++;
			} else {
				if (tops > 0) {
					// Merging path element with corresponding to top path.
					tops--;
				} else {
					// Normal path element found.
					pathElements.add(0, pathArray[i]);
				}
			}
		}

		// Remaining top paths need to be retained.
		for (int i = 0; i < tops; i++) {
			pathElements.add(0, TOP_PATH);
		}

		return prefix
				+ collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
	}

	/**
	 * Compare two paths after normalization of them.
	 * 
	 * @param path1
	 *            First path for comparizon
	 * @param path2
	 *            Second path for comparizon
	 * @return whether the two paths are equivalent after normalization
	 */
	public static boolean pathEquals(String path1, String path2) {
		return cleanPath(path1).equals(cleanPath(path2));
	}

	/**
	 * Parse the given locale string into a <code>java.util.Locale</code>. This
	 * is the inverse operation of Locale's <code>toString</code>.
	 * 
	 * @param localeString
	 *            the locale string, following <code>java.util.Locale</code>'s
	 *            toString format ("en", "en_UK", etc). Also accepts spaces as
	 *            separators, as alternative to underscores.
	 * @return a corresponding Locale instance
	 */
	public static Locale parseLocaleString(String localeString) {
		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
		String language = (parts.length > 0 ? parts[0] : "");
		String country = (parts.length > 1 ? parts[1] : "");
		String variant = (parts.length > 2 ? parts[2] : "");
		return (language.length() > 0 ? new Locale(language, country, variant)
				: null);
	}

	// ---------------------------------------------------------------------
	// Convenience methods for working with String arrays
	// ---------------------------------------------------------------------

	/**
	 * Append the given String to the given String array, returning a new array
	 * consisting of the input array contents plus the given String.
	 * 
	 * @param array
	 *            the array to append to (can be <code>null</code>)
	 * @param str
	 *            the String to append
	 * @return the new array (never <code>null</code>)
	 */
	public static String[] addStringToArray(String[] array, String str) {
		if (array == null || array.length < 0) {
			return new String[] { str };
		}
		String[] newArr = new String[array.length + 1];
		System.arraycopy(array, 0, newArr, 0, array.length);
		newArr[array.length] = str;
		return newArr;
	}

	/**
	 * Turn given source String array into sorted array.
	 * 
	 * @param array
	 *            the source array
	 * @return the sorted array (never <code>null</code>)
	 */
	public static String[] sortStringArray(String[] array) {
		if (array == null || array.length < 0) {
			return new String[0];
		}
		Arrays.sort(array);
		return array;
	}

	/**
	 * Copy the given Collection into a String array. The Collection must
	 * contain String elements only.
	 * 
	 * @param collection
	 *            the Collection to copy
	 * @return the String array (<code>null</code> if the Collection was
	 *         <code>null</code> as well)
	 */
	public static String[] toStringArray(Collection collection) {
		if (collection == null) {
			return null;
		}
		return (String[]) collection.toArray(new String[collection.size()]);
	}

	/**
	 * Remove duplicate Strings from the given array. Also sorts the array, as
	 * it uses a TreeSet.
	 * 
	 * @param array
	 *            the String array
	 * @return an array without duplicates, in natural sort order
	 */
	public static String[] removeDuplicateStrings(String[] array) {
		if (array == null || array.length < 0) {
			return array;
		}
		Set set = new TreeSet();
		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}
		return toStringArray(set);
	}

	/**
	 * Split a String at the first occurrence of the delimiter. Does not include
	 * the delimiter in the result.
	 * 
	 * @param toSplit
	 *            the string to split
	 * @param delimiter
	 *            to split the string up with
	 * @return a two element array with index 0 being before the delimiter, and
	 *         index 1 being after the delimiter (neither element includes the
	 *         delimiter); or <code>null</code> if the delimiter wasn't found in
	 *         the given input String
	 */
	public static String[] split2(String toSplit, String delimiter) {
		if (!hasLength(toSplit) || !hasLength(delimiter)) {
			return null;
		}
		int offset = toSplit.indexOf(delimiter);
		if (offset < 0) {
			return null;
		}
		String beforeDelimiter = toSplit.substring(0, offset);
		String afterDelimiter = toSplit.substring(offset + delimiter.length());
		return new String[] { beforeDelimiter, afterDelimiter };
	}

	/**
	 * Take an array Strings and split each element based on the given
	 * delimiter. A <code>Properties</code> instance is then generated, with the
	 * left of the delimiter providing the key, and the right of the delimiter
	 * providing the value.
	 * <p>
	 * Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * 
	 * @param array
	 *            the array to process
	 * @param delimiter
	 *            to split each element using (typically the equals symbol)
	 * @return a <code>Properties</code> instance representing the array
	 *         contents, or <code>null</code> if the array to process was null
	 *         or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array,
															  String delimiter) {
		return splitArrayElementsIntoProperties(array, delimiter, null);
	}

	/**
	 * Take an array Strings and split each element based on the given
	 * delimiter. A <code>Properties</code> instance is then generated, with the
	 * left of the delimiter providing the key, and the right of the delimiter
	 * providing the value.
	 * <p>
	 * Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * 
	 * @param array
	 *            the array to process
	 * @param delimiter
	 *            to split each element using (typically the equals symbol)
	 * @param charsToDelete
	 *            one or more characters to remove from each element prior to
	 *            attempting the split operation (typically the quotation mark
	 *            symbol), or <code>null</code> if no removal should occur
	 * @return a <code>Properties</code> instance representing the array
	 *         contents, or <code>null</code> if the array to process was null
	 *         or empty
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array,
															  String delimiter, String charsToDelete) {

		if (array == null || array.length == 0) {
			return null;
		}

		Properties result = new Properties();
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			if (charsToDelete != null) {
				element = deleteAny(array[i], charsToDelete);
			}
			String[] splittedElement = split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(), splittedElement[1]
					.trim());
		}
		return result;
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * Trims tokens and omits empty tokens.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 * 
	 * @param str
	 *            the String to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as String (each of those
	 *            characters is individually considered as delimiter).
	 * @return an array of the tokens
	 * @see StringTokenizer
	 * @see String#trim
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of
	 * delimiter characters. Each of those characters can be used to separate
	 * tokens. A delimiter is always a single character; for multi-character
	 * delimiters, consider using <code>delimitedListToStringArray</code>
	 *
	 * @param str
	 *            the String to tokenize
	 * @param delimiters
	 *            the delimiter characters, assembled as String (each of those
	 *            characters is individually considered as delimiter)
	 * @param trimTokens
	 *            trim the tokens via String's <code>trim</code>
	 * @param ignoreEmptyTokens
	 *            omit empty tokens from the result array (only applies to
	 *            tokens that are empty after trimming; StringTokenizer will not
	 *            consider subsequent delimiters as token in the first place).
	 * @return an array of the tokens
	 * @see StringTokenizer
	 * @see String#trim
	 * @see #delimitedListToStringArray
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters,
												 boolean trimTokens, boolean ignoreEmptyTokens) {

		StringTokenizer st = new StringTokenizer(str, delimiters);
		List tokens = new ArrayList();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>
	 * A single delimiter can consists of more than one character: It will still
	 * be considered as single delimiter string, rather than as bunch of
	 * potential delimiter characters - in contrast to
	 * <code>tokenizeToStringArray</code>.
	 *
	 * @param str
	 *            the input String
	 * @param delimiter
	 *            the delimiter between elements (this is a single delimiter,
	 *            rather than a bunch individual delimiter characters)
	 * @return an array of the tokens in the list
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(String str,
													  String delimiter) {
		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] { str };
		}

		List result = new ArrayList();
		if ("".equals(delimiter)) {
			for (int i = 0; i < str.length(); i++) {
				result.add(str.substring(i, i + 1));
			}
		} else {
			int pos = 0;
			int delPos = 0;
			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
				result.add(str.substring(pos, delPos));
				pos = delPos + delimiter.length();
			}
			if (str.length() > 0 && pos <= str.length()) {
				// Add rest of String, but not in case of empty input.
				result.add(str.substring(pos));
			}
		}
		return toStringArray(result);
	}

	/**
	 * Convert a CSV list into an array of Strings.
	 *
	 * @param str
	 *            CSV list
	 * @return an array of Strings, or the empty array if s is null
	 */
	public static String[] commaDelimitedListToStringArray(String str) {
		return delimitedListToStringArray(str, ",");
	}

	/**
	 * Convenience method to convert a CSV string list to a set. Note that this
	 * will suppress duplicates.
	 *
	 * @param str
	 *            CSV String
	 * @return a Set of String entries in the list
	 */
	public static Set commaDelimitedListToSet(String str) {
		Set set = new TreeSet();
		String[] tokens = commaDelimitedListToStringArray(str);
		for (int i = 0; i < tokens.length; i++) {
			set.add(tokens[i]);
		}
		return set;
	}

	/**
	 * Convenience method to return a String array as a delimited (e.g. CSV)
	 * String. E.g. useful for toString() implementations.
	 *
	 * @param arr
	 *            array to display. Elements may be of any type (toString will
	 *            be called on each element).
	 * @param delim
	 *            delimiter to use (probably a ",")
	 */
	public static String arrayToDelimitedString(Object[] arr, String delim) {
		if (arr == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV)
	 * String. E.g. useful for toString() implementations.
	 *
	 * @param coll
	 *            Collection to display
	 * @param delim
	 *            delimiter to use (probably a ",")
	 * @param prefix
	 *            string to start each element with
	 * @param suffix
	 *            string to end each element with
	 */
	public static String collectionToDelimitedString(Collection coll,
													 String delim, String prefix, String suffix) {
		if (coll == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		Iterator it = coll.iterator();
		int i = 0;
		while (it.hasNext()) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(prefix).append(it.next()).append(suffix);
			i++;
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV)
	 * String. E.g. useful for toString() implementations.
	 *
	 * @param coll
	 *            Collection to display
	 * @param delim
	 *            delimiter to use (probably a ",")
	 */
	public static String collectionToDelimitedString(Collection coll,
													 String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	/**
	 * Convenience method to return a String array as a CSV String. E.g. useful
	 * for toString() implementations.
	 *
	 * @param arr
	 *            array to display. Elements may be of any type (toString will
	 *            be called on each element).
	 */
	public static String arrayToCommaDelimitedString(Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	/**
	 * Convenience method to return a Collection as a CSV String. E.g. useful
	 * for toString() implementations.
	 *
	 * @param coll
	 *            Collection to display
	 */
	public static String collectionToCommaDelimitedString(Collection coll) {
		return collectionToDelimitedString(coll, ",");
	}

	/**
	 * 中文转换
	 *
	 * @param source
	 * @return
	 */
	public static String[] getGBK(String[] source) {
		if (source == null) {
			return null;
		}
		String[] buffer = new String[source.length];
		for (int i = 0; i < source.length; i++) {
			buffer[i] = getGBK(source[i]);
		}
		return buffer;
	}

	public static String getGBK(String source) {
		if (source == null) {
			return null;
		}
		String target = null;
		try {
			byte[] b = source.getBytes("ISO8859_1");
			target = new String(b, "GBK");
		} catch (Exception e) {
		}
		return target;
	}

	public static String[] getISO8859_1(String[] source) {
		String[] buffer = new String[source.length];
		for (int i = 0; i < source.length; i++) {
			buffer[i] = getISO8859_1(source[i]);
		}
		return buffer;
	}

	public static String getISO8859_1(String source) {
		String target = null;
		try {
			byte[] b = source.getBytes("GBK");
			target = new String(b, "ISO8859_1");
		} catch (Exception e) {
		}
		return target;
	}

	/**
	 * 字符串替换
	 *
	 * @param mainStr
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
	public static String replace2(String mainStr, String oldStr, String newStr) {
		StringBuffer buffer = new StringBuffer(mainStr);
		int index = 0;
		int begin = 0;
		while ((index = buffer.toString().indexOf(oldStr, begin)) > -1) {
			buffer.replace(index, index + oldStr.length(), newStr);
			begin = index + newStr.length();
		}
		return buffer.toString();
	}

	/**
	 * 用于对象的类型转换
	 *
	 * @param type
	 * @param var
	 * @return
	 */
	public static Object convert(Class type, String var) throws Exception {
		if (type.getName().equalsIgnoreCase("java.lang.String")) {
			return var;
		} else if (type.getName().equalsIgnoreCase("java.math.BigDecimal")) {
			return new java.math.BigDecimal(var);
		} else if (type.getName().equalsIgnoreCase("java.sql.Date")
				|| type.getName().equalsIgnoreCase("java.util.Date")) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse(var);
			} catch (ParseException e) {
				throw new Exception(e);
			}
		} else if (type.getName().equalsIgnoreCase("java.lang.Long")
				|| type.getName().equalsIgnoreCase("long")) {
			return new Long(var);
		} else if (type.getName().equalsIgnoreCase("java.lang.Integer")
				|| type.getName().equalsIgnoreCase("int")) {
			return new Integer(var);
		} else if (type.getName().equalsIgnoreCase("java.lang.Boolean")
				|| type.getName().equalsIgnoreCase("boolean")) {
			return new Boolean(var);
		} else {
			return null;
		}
	}

	/**
	 * 字符串是否包含在字符数组中
	 *
	 * @param value
	 * @param array
	 * @return
	 */
	public static boolean contains(String value, String[] array) {
		if (array != null) {
			for (int i = 0; i < array.length; ++i) {
				if (value != null && array[i] != null && array[i].equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 字符串是否包含在字符数组中
	 *
	 * @return
	 */
	public static boolean contains(String mainStr, String subStr) {
		int index = mainStr.indexOf(subStr);
		boolean retflag = (index > -1) ? true : false;
		return retflag;
	}

	/**
	 * 加密
	 *
	 * @param str
	 * @return
	 */
	public static String crypt(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); // 所用的加密算法
			md.update(str.getBytes());
			byte[] digestArr = md.digest(); // 计算摘要,加密
			String aaa = byte2hex(digestArr);
			return aaa;
		} catch (Exception e) {
			return "error!";
		}

	}

	/**
	 * 加密辅助
	 *
	 * @param b
	 * @return
	 */

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
				// if(n<b.length-1)
				// hs = hs + ":";
			}
		}
		return hs.toUpperCase();
	}

	/**
	 * 生成随机数 flag为位数,如flag=8,则生成8位随机数
	 *
	 * @param flag
	 * @return
	 */
	public static String getRandom(int flag) {
		int i;
		String randomStr = "";
		for (int j = 1; j < flag; j++) {
			i = (int) (Math.random() * 10);
			randomStr += String.valueOf(i);
		}
		return randomStr;
	}

	/**
	 * 字符数组转换成字符串
	 *
	 * @param array
	 * @param token
	 * @return
	 */
	public static String StringArrayToString(String[] array, String token) {
		if (array != null) {
			String separator = token == null ? "," : token;
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				if (i > 0) {
					buffer.append(separator);
				}
				buffer.append(array[i]);
			}
			return buffer.toString();
		}
		return null;
	}

	/**
	 * 字符串转换成字符数组
	 *
	 * @param var
	 * @param token
	 * @return
	 */
	public static String[] StringToStringArray(String var, String token) {
		String separator = token == null ? "," : token;
		if (var != null) {
			StringTokenizer st = new StringTokenizer(var, separator);
			String[] buffer = new String[st.countTokens()];
			int t = 0;
			while (st.hasMoreTokens()) {
				buffer[t] = st.nextToken();
				t++;
			}
			return buffer;
		}
		return null;
	}

	/**
	 * 字符串处理，写SQL语句
	 *
	 * @param strSource
	 * @return
	 */
	public static String toSqlString(String strSource) {
		String strResult;
		if (strSource == null) {
			strResult = "null";
		} else {
			strResult = replace(strSource, "'", "''");
			strResult = replace(strResult, "&lt;", "<");
			strResult = replace(strResult, "&gt;", ">");
			strResult = "'" + strResult + "'";
		}
		return strResult;
	}

	/**
	 * 说明:用于html页面中'null'转换
	 *
	 * @param obj
	 * @param str
	 * @return
	 */
	public static String changeNullToStr(Object obj, String str) {
		if (obj == null || obj.toString().equals("null")) {
			return str;
		} else {
			return obj.toString();
		}
	}

	/**
	 * 说明:用于html页面中'null'转换为""
	 *
	 * @param obj
	 * @return
	 */
	public static String changeNullToStr(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * 把字符串第一个字母转换成大写
	 *
	 * @param str
	 * @return
	 */
	public static String firstCharToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 把字符串第一个字母转换成小写
	 *
	 * @param str
	 * @return
	 */
	public static String firstCharToLowerCase(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	/**
	 * 把字符串转换成clob
	 *
	 * @param str
	 * @return
	 * @throws SystemException
	 */
	/*
	 * @SuppressWarnings("deprecation") public static Clob
	 * converStringToClob(String str) throws SystemException { if (str == null)
	 * { return null; } try { Clob clob = CLOB.empty_lob(); ((Datum)
	 * clob).setBytes(str.getBytes()); return clob; } catch (SQLException e) {
	 * throw new SystemException(e); } }
	 */
	/**
	 * 把clob转换成字符串
	 *
	 * @param clob
	 * @return
	 * @throws SystemException
	 */
	/*
	 * public static String converClobToString(Clob clob) throws SystemException
	 * { CLOB clobvalue = (CLOB) clob; if (clob == null) { return null; } byte[]
	 * bytes = clobvalue.getBytes(); return bytes == null ? null : new
	 * String(bytes); }
	 */
	/**
	 * 分割字串
	 *
	 * @param source
	 *            原始字符
	 * @param div
	 *            分割符
	 * @return 字符串数组
	 */
	public static String[] split(String source, String div) {
		int arynum = 0, intIdx = 0, intIdex = 0, div_length = div.length();
		if (source.compareTo("") != 0) {
			if (source.indexOf(div) != -1) {
				intIdx = source.indexOf(div);
				for (int intCount = 1;; intCount++) {
					if (source.indexOf(div, intIdx + div_length) != -1) {
						intIdx = source.indexOf(div, intIdx + div_length);
						arynum = intCount;
					} else {
						arynum += 2;
						break;
					}
				}
			} else {
				arynum = 1;
			}
		} else {
			arynum = 0;

		}
		intIdx = 0;
		intIdex = 0;
		String[] returnStr = new String[arynum];

		if (source.compareTo("") != 0) {
			if (source.indexOf(div) != -1) {
				intIdx = (int) source.indexOf(div);
				returnStr[0] = (String) source.substring(0, intIdx);
				for (int intCount = 1;; intCount++) {
					if (source.indexOf(div, intIdx + div_length) != -1) {
						intIdex = (int) source
								.indexOf(div, intIdx + div_length);
						returnStr[intCount] = (String) source.substring(intIdx
								+ div_length, intIdex);
						intIdx = (int) source.indexOf(div, intIdx + div_length);
					} else {
						returnStr[intCount] = (String) source.substring(intIdx
								+ div_length, source.length());
						break;
					}
				}
			} else {
				returnStr[0] = (String) source.substring(0, source.length());
				return returnStr;
			}
		} else {
			return returnStr;
		}
		return returnStr;
	}

	/*
	 * 使用方法：主要为替换sql语句中的?. s:要替换的原sql splitchar:要替换的字符中，在此为"?"
	 * parms[]:长度必需和?个数一样，
	 */
	public static String replaceSqlByArray(String sql, String splitchar,
										   Object parms[]) {
		String afterSplits[] = StringUtils.split(sql, splitchar);
		String retStr = "";
		if (parms.length == afterSplits.length - 1) {
			for (int i = 0; i < parms.length; i++) {
				retStr = retStr + afterSplits[i] + parms[i].toString();
			}
		} else {
			log.debug("sql中的?和参数数据中的个数不相符");
		}
		return retStr;
	}

	/**
	 * 将字符串格式成html格式
	 *
	 * @param str
	 *            被转换的字符串
	 * @return String if str is not null ,otherwise return null
	 */
	public static String formatStringToHtml(String str) {
		if (str == null) {
			return "";
		}
		str = replace(str, "\n", "<br>");
		str = replace(str, "\'", "\\\'");
		str = replace(str, "\"", "\\\"");
		str = replace(str, " ", "&nbsp;");
		return str;
	}

	/**
	 * 说明:开始日期与结束日期输入是否合法,true为合法
	 *
	 * @param start_time
	 * @param end_time
	 * @return
	 * @throws SystemException
	 */
	/*
	 * public static boolean dateIsLegality(DateElement start_time, DateElement
	 * end_time) throws SystemException { if (start_time.getValue() != null &&
	 * end_time.getValue() != null) { //为正确的日期格式 if (start_time.validate() &&
	 * end_time.validate()) { long start = start_time.getDateValue().getTime();
	 * long end = end_time.getDateValue().getTime(); if
	 * (start_time.getDay().getValue() != null && end_time.getDay().getValue()
	 * != null) { if (start - end >= 0) { return false; } } else { if (start -
	 * end > 0) { return false; } } } } return true; }
	 */
	/**
	 * 判断字符串是否为null或者空。
	 *
	 * @param str
	 *            待判断的字符串
	 * @return boolean 判断结果
	 */
	public static boolean isBlank(String str) {
		return (str == null) || (str.equals(""));
	}

	public static boolean isNotBlank (String str) {
		return str != null && !str.equals("");
	}

	/**
	 * 从HttpServletRequest对象中取字符串数组。
	 *
	 * @param oRequest
	 *            HttpServletRequest对象
	 * @param strParamName
	 *            参数名
	 * @param strDefaultValue
	 *            默认值
	 * @return 字符串数组
	 */
	public static String[] getStringArray(HttpServletRequest oRequest,
										  String strParamName, String strDefaultValue) {
		if (oRequest == null || StringUtils.isBlank(strParamName)) {
			return null;
		}
		String[] strParameterValues = oRequest.getParameterValues(strParamName);
		if (strParameterValues == null) {
			strParameterValues = new String[0];
		}
		for (int i = 0; i < strParameterValues.length; i++) {
			if (strParameterValues[i] == null
					|| strParameterValues[i].equals("")) {
				strParameterValues[i] = strDefaultValue;
			}
		}
		return strParameterValues;
	}

	/**
	 * 大写字母转换为小写字母或小写转换为大写,flag=1转换为大写,flag=2转换为小写
	 *
	 * @param str
	 * @param flag
	 * @return
	 */
	public static String capitalToLowercase(String str, int flag) {
		String[] lowercass = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
				"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
				"v", "w", "x", "y", "z" };
		String[] capital = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
				"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			String sub_str = String.valueOf(str.charAt(i));
			// 转换为小写
			if (flag == 1) {
				for (int j = 0; j < lowercass.length; j++) {
					String replace_str = lowercass[j];
					if (sub_str.equalsIgnoreCase(replace_str)) {
						sb.append(replace_str);
					}
				}
			}
			// 转换为大写
			else if (flag == 2) {
				for (int j = 0; j < capital.length; j++) {
					String replace_str = capital[j];
					if (sub_str.equalsIgnoreCase(replace_str)) {
						sb.append(replace_str);
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 从字符串中解析属性，字符串格式为：“”
	 *
	 * @param initString
	 * @return
	 */
	public static Map ConvertStringToProperties(String initString) {
		HashMap properties = new HashMap();
		if (initString == null) {
			return properties;
		}
		StringTokenizer pst = new StringTokenizer(initString, ";");
		String property = null;
		String name = null;
		String value = null;
		StringTokenizer nst = null;
		while (pst.hasMoreTokens()) {
			property = pst.nextToken();
			nst = new StringTokenizer(property, "=");
			if (nst.countTokens() == 2) {
				name = nst.nextToken().trim().toLowerCase();
				value = nst.nextToken().trim();
				properties.put(name, value);
			}
		}
		return properties;
	}

	/**
	 * 判断字符串第一个字符是否包含数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if ("0123456789".indexOf(str.substring(0, 1)) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断字符串第一个字符是否包含大小写字符
	 *
	 * @param str
	 * @return
	 */
	public static boolean isLetter(String str) {
		if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".indexOf(str
				.substring(0, 1)) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断字符串第一个字符是否包含特殊符号
	 *
	 * @param str
	 * @return
	 */
	public static boolean isSymbol(String str) {
		if ("!@#$%^&*()_+-=|[]{}'\",./?<>\\".indexOf(str.substring(0, 1)) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断字符中是否在数组中
	 */
	public static int ArrayIndexOf(String[] array, String str) {
		int index = -1;
		if (array == null) {
			return index;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(str)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * 将对象转成String 对象
	 */
	public static String converObjectToString(Object obj) throws Exception {
		Class typeName = obj.getClass();
		if (typeName.getName().equalsIgnoreCase("java.lang.String")) {
			return (String) obj;
		} else if (typeName.getName().equalsIgnoreCase("java.lang.Integer")) {
			return String.valueOf(obj);
		} else if (typeName.getName().equalsIgnoreCase("java.lang.Long")) {
			return String.valueOf(obj);
		} else if (typeName.getName().equalsIgnoreCase("java.lang.Float")) {
			return String.valueOf(obj);
		} else if (typeName.getName().equalsIgnoreCase("java.lang.Double")) {
			return String.valueOf(obj);
		} else if (typeName.getName().equalsIgnoreCase("java.sql.Date")) {
			return obj + "";
		} else if (typeName.getName().equalsIgnoreCase("java.util.Date")) {
			return obj + "";
		} else if (typeName.getName().equalsIgnoreCase("java.math.BigDecimal")) {
			return obj.toString();
		} else if (typeName.getName().equalsIgnoreCase("java.sql.Timestamp")) {
			return obj.toString();
		}
		return null;

	}

	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * 获取类路径中的资源文件的物理文件路径. NOTE: 仅在 Win32 平台下测试通过开发.
	 *
	 * @date 2005.10.16
	 * @param resourcePath
	 *            资源路径
	 * @return 配置文件路径
	 */
	public static String getRealFilePath(String resourcePath) {
		URL inputURL = StringUtils.class.getResource(resourcePath);

		String filePath = inputURL.getFile();

		// For windows platform, the filePath will like this:
		// /E:/Push/web/WEB-INF/classes/studio/beansoft/smtp/MailSender.ini
		// So must remove the first /

		if (filePath.startsWith("/")) {
			filePath = filePath.substring(1);
		}

		return filePath;
	}

	/**
	 * 将字符串转换为 int.
	 *
	 * @param input
	 *            输入的字串
	 * @date 2005-07-29
	 * @return 结果数字
	 */
	public static int parseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	/**
	 * 返回 HTTP 请求的 Referer, 如果没有, 就返回默认页面值.
	 *
	 * 仅用于移动博客开发页面命名风格: // Added at 2004-10-12 // 如果前一页面的地址包含 _action.jsp ,
	 * 为了避免链接出错, 就返回默认页面
	 *
	 * 2006-08-02 增加从 url 参数 referer 的判断
	 *
	 * @param request
	 *            - HttpServletRequest 对象
	 * @param defaultPage
	 *            - String, 默认页面
	 * @return String - Referfer
	 */
	public static String getReferer(HttpServletRequest request,
									String defaultPage) {
		String referer = request.getHeader("Referer");// 前一页面的地址, 提交结束后返回此页面

		// 获取URL中的referer参数
		String refererParam = request.getParameter("referer");

		if (!isEmpty(refererParam)) {
			referer = refererParam;
		}

		// Added at 2004-10-12
		// 如果前一页面的地址包含 _action.jsp , 为了避免链接出错, 就返回默认页面
		if (isEmpty(referer) || referer.indexOf("_action.jsp") != -1) {
			referer = defaultPage;
		}

		return referer;
	}


	/**
	 * Get the base path of this request.
	 *
	 * @param request
	 *            - HttpServletRequest
	 * @return String - the base path, eg: http://www.abc.com:8000/someApp/
	 */
	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}

	/**
	 * Get the current page's full path of this request. 获取当前页的完整访问 URL 路径.
	 *
	 * @author BeanSoft
	 * @date 2005-08-01
	 * @param request
	 *            - HttpServletRequest
	 * @return String - the full url path, eg:
	 *         http://www.abc.com:8000/someApp/index.jsp?param=abc
	 */
	public static String getFullRequestURL(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String qString = request.getQueryString();

		if (qString != null) {
			url.append('?');
			url.append(qString);
		}

		return url.toString();
	}

	/**
	 * Get the current page's full path of this request. 获取当前页的完整访问 URI 路径.
	 *
	 * @author BeanSoft
	 * @date 2005-08-01
	 * @param request
	 *            - HttpServletRequest
	 * @return String - the full uri path, eg: /someApp/index.jsp?param=abc
	 */
	public static String getFullRequestURI(HttpServletRequest request) {
		StringBuffer url = new StringBuffer(request.getRequestURI());
		String qString = request.getQueryString();

		if (qString != null) {
			url.append('?');
			url.append(qString);
		}

		return url.toString();
	}

	// ------------------------------------ 字符串处理方法
	// ----------------------------------------------

	/**
	 * 将字符串 source 中的 oldStr 替换为 newStr, 并以大小写敏感方式进行查找
	 *
	 * @param source
	 *            需要替换的源字符串
	 * @param oldStr
	 *            需要被替换的老字符串
	 * @param newStr
	 *            替换为的新字符串
	 */
	public static String replace(String source, String oldStr, String newStr) {
		return replace(source, oldStr, newStr, true);
	}

	/**
	 * 将字符串 source 中的 oldStr 替换为 newStr, matchCase 为是否设置大小写敏感查找
	 *
	 * @param source
	 *            需要替换的源字符串
	 * @param oldStr
	 *            需要被替换的老字符串
	 * @param newStr
	 *            替换为的新字符串
	 * @param matchCase
	 *            是否需要按照大小写敏感方式查找
	 */
	public static String replace(String source, String oldStr, String newStr,
								 boolean matchCase) {
		if (source == null) {
			return null;
		}
		// 首先检查旧字符串是否存在, 不存在就不进行替换
		if (source.toLowerCase().indexOf(oldStr.toLowerCase()) == -1) {
			return source;
		}
		int findStartPos = 0;
		int a = 0;
		while (a > -1) {
			int b = 0;
			String str1, str2, str3, str4, strA, strB;
			str1 = source;
			str2 = str1.toLowerCase();
			str3 = oldStr;
			str4 = str3.toLowerCase();
			if (matchCase) {
				strA = str1;
				strB = str3;
			} else {
				strA = str2;
				strB = str4;
			}
			a = strA.indexOf(strB, findStartPos);
			if (a > -1) {
				b = oldStr.length();
				findStartPos = a + b;
				StringBuffer bbuf = new StringBuffer(source);
				source = bbuf.replace(a, a + b, newStr) + "";
				// 新的查找开始点位于替换后的字符串的结尾
				findStartPos = findStartPos + newStr.length() - b;
			}
		}
		return source;
	}

	/**
	 * 清除字符串结尾的空格.
	 *
	 * @param input
	 *            String 输入的字符串
	 * @return 转换结果
	 */
	public static String trimTailSpaces(String input) {
		if (isEmpty(input)) {
			return "";
		}

		String trimedString = input.trim();

		if (trimedString.length() == input.length()) {
			return input;
		}

		return input.substring(0, input.indexOf(trimedString)
				+ trimedString.length());
	}

	/**
	 * Change the null string value to "", if not null, then return it self, use
	 * this to avoid display a null string to "null".
	 *
	 * @param input
	 *            the string to clear
	 * @return the result
	 */
	public static String clearNull(String input) {
		return isEmpty(input) ? "" : input;
	}

	/**
	 * Return the limited length string of the input string (added at:April 10,
	 * 2004).
	 *
	 * @param input
	 *            String
	 * @param maxLength
	 *            int
	 * @return String processed result
	 */
	public static String limitStringLength(String input, int maxLength) {
		if (isEmpty(input))
			return "";

		if (input.length() <= maxLength) {
			return input;
		} else {
			return input.substring(0, maxLength - 3) + "...";
		}

	}

	/**
	 * 将字符串转换为一个 JavaScript 的 alert 调用. eg: htmlAlert("What?"); returns
	 * &lt;SCRIPT language="JavaScript"&gt;alert("What?")&lt;/SCRIPT&gt;
	 *
	 * @param message
	 *            需要显示的信息
	 * @return 转换结果
	 */
	public static String scriptAlert(String message) {
		return "<SCRIPT language=\"JavaScript\">alert(\"" + message
				+ "\");</SCRIPT>";
	}

	/**
	 * 将字符串转换为一个 JavaScript 的 document.location 改变调用. eg: htmlAlert("a.jsp");
	 * returns &lt;SCRIPT
	 * language="JavaScript"&gt;document.location="a.jsp";&lt;/SCRIPT&gt;
	 *
	 * @param url
	 *            需要显示的 URL 字符串
	 * @return 转换结果
	 */
	public static String scriptRedirect(String url) {
		return "<SCRIPT language=\"JavaScript\">document.location=\"" + url
				+ "\";</SCRIPT>";
	}

	/**
	 * 返回脚本语句 &lt;SCRIPT language="JavaScript"&gt;history.back();&lt;/SCRIPT&gt;
	 *
	 * @return 脚本语句
	 */
	public static String scriptHistoryBack() {
		return "<SCRIPT language=\"JavaScript\">history.back();</SCRIPT>";
	}

	/**
	 * 滤除帖子中的危险 HTML 代码, 主要是脚本代码, 滚动字幕代码以及脚本事件处理代码
	 *
	 * @param content
	 *            需要滤除的字符串
	 * @return 过滤的结果
	 */
	public static String replaceHtmlCode(String content) {
		if (isEmpty(content)) {
			return "";
		}
		// 需要滤除的脚本事件关键字
		String[] eventKeywords = { "onmouseover", "onmouseout", "onmousedown",
				"onmouseup", "onmousemove", "onclick", "ondblclick",
				"onkeypress", "onkeydown", "onkeyup", "ondragstart",
				"onerrorupdate", "onhelp", "onreadystatechange", "onrowenter",
				"onrowexit", "onselectstart", "onload", "onunload",
				"onbeforeunload", "onblur", "onerror", "onfocus", "onresize",
				"onscroll", "oncontextmenu" };
		content = replace(content, "<script", "&ltscript", false);
		content = replace(content, "</script", "&lt/script", false);
		content = replace(content, "<marquee", "&ltmarquee", false);
		content = replace(content, "</marquee", "&lt/marquee", false);

		// 滤除脚本事件代码
		for (int i = 0; i < eventKeywords.length; i++) {
			content = replace(content, eventKeywords[i],
					"_" + eventKeywords[i], false); // 添加一个"_", 使事件代码无效
		}
		return content;
	}

	/**
	 * 滤除 HTML 代码 为文本代码.
	 */
	public static String replaceHtmlToText(String input) {
		if (isEmpty(input)) {
			return "";
		}
		return setBr(setTag(input));
	}

	/**
	 * 滤除 HTML 标记. 因为 XML 中转义字符依然有效, 因此把特殊字符过滤成中文的全角字符.
	 *
	 * @author beansoft
	 * @param s
	 *            输入的字串
	 * @return 过滤后的字串
	 */
	public static String setTag(String s) {
		int j = s.length();
		StringBuffer stringbuffer = new StringBuffer(j + 500);
		char ch;
		for (int i = 0; i < j; i++) {
			ch = s.charAt(i);
			if (ch == '<') {
				stringbuffer.append("&lt");
				// stringbuffer.append("〈");
			} else if (ch == '>') {
				stringbuffer.append("&gt");
				// stringbuffer.append("〉");
			} else if (ch == '&') {
				stringbuffer.append("&amp");
				// stringbuffer.append("〃");
			} else if (ch == '%') {
				stringbuffer.append("%%");
				// stringbuffer.append("※");
			} else {
				stringbuffer.append(ch);
			}
		}

		return stringbuffer.toString();
	}

	/** 滤除 BR 代码 */
	public static String setBr(String s) {
		int j = s.length();
		StringBuffer stringbuffer = new StringBuffer(j + 500);
		for (int i = 0; i < j; i++) {

			if (s.charAt(i) == '\n' || s.charAt(i) == '\r') {
				continue;
			}
			stringbuffer.append(s.charAt(i));
		}

		return stringbuffer.toString();
	}

	/** 滤除空格 */
	public static String setNbsp(String s) {
		int j = s.length();
		StringBuffer stringbuffer = new StringBuffer(j + 500);
		for (int i = 0; i < j; i++) {
			if (s.charAt(i) == ' ') {
				stringbuffer.append("&nbsp;");
			} else {
				stringbuffer.append(s.charAt(i) + "");
			}
		}
		return stringbuffer.toString();
	}

	/**
	 * 判断字符串是否全是数字字符.
	 *
	 * @param input
	 *            输入的字符串
	 * @return 判断结果, true 为全数字, false 为还有非数字字符
	 */
	public static boolean isNumeric(String input) {
		if (isEmpty(input)) {
			return false;
		}

		for (int i = 0; i < input.length(); i++) {
			char charAt = input.charAt(i);

			if (!Character.isDigit(charAt)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 转换由表单读取的数据的内码(从 ISO8859 转换到 gb2312).
	 *
	 * @param input
	 *            输入的字符串
	 * @return 转换结果, 如果有错误发生, 则返回原来的值
	 */
	public static String toChi(String input) {
		try {
			byte[] bytes = input.getBytes("ISO8859-1");
			return new String(bytes, "GBK");
		} catch (Exception ex) {
		}
		return input;
	}

	/**
	 * 转换由表单读取的数据的内码到 ISO(从 GBK 转换到ISO8859-1).
	 *
	 * @param input
	 *            输入的字符串
	 * @return 转换结果, 如果有错误发生, 则返回原来的值
	 */
	public static String toISO(String input) {
		return changeEncoding(input, "GBK", "ISO8859-1");
	}

	/**
	 * 转换字符串的内码.
	 *
	 * @param input
	 *            输入的字符串
	 * @param sourceEncoding
	 *            源字符集名称
	 * @param targetEncoding
	 *            目标字符集名称
	 * @return 转换结果, 如果有错误发生, 则返回原来的值
	 */
	public static String changeEncoding(String input, String sourceEncoding,
										String targetEncoding) {
		if (input == null || input.equals("")) {
			return input;
		}

		try {
			byte[] bytes = input.getBytes(sourceEncoding);
			return new String(bytes, targetEncoding);
		} catch (Exception ex) {
		}
		return input;
	}

	/**
	 * 将单个的 ' 换成 ''; SQL 规则:如果单引号中的字符串包含一个嵌入的引号,可以使用两个单引号表示嵌入的单引号.
	 */

	public static String replaceSql(String input) {
		return replace(input, "'", "''");
	}

	/**
	 * 对给定字符进行 URL 编码
	 */
	public static String encode(String value) {
		if (isEmpty(value)) {
			return "";
		}

		try {
			value = java.net.URLEncoder.encode(value, "GB2312");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return value;
	}

	/**
	 * 对给定字符进行 URL 解码
	 *
	 * @param value
	 *            解码前的字符串
	 * @return 解码后的字符串
	 */
	public static String decode(String value) {
		if (isEmpty(value)) {
			return "";
		}

		try {
			return java.net.URLDecoder.decode(value, "GB2312");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return value;
	}

	/**
	 * 判断字符串是否未空, 如果为 null 或者长度为0, 均返回 true.
	 */
	public static boolean isEmpty(String input) {
		return (input == null || input.length() == 0);
	}

	public static boolean isObjEmpty(Object obj) {

		if (null == obj) {
			return true;
		} else {
			String input = obj.toString();
			return isEmpty(input);
		}
	}

	/**
	 * 获得输入字符串的字节长度(即二进制字节数), 用于发送短信时判断是否超出长度.
	 *
	 * @param input
	 *            输入字符串
	 * @return 字符串的字节长度(不是 Unicode 长度)
	 */
	public static int getBytesLength(String input) {
		if (input == null) {
			return 0;
		}

		int bytesLength = input.getBytes().length;

		// logger.debug("bytes length is:" + bytesLength);

		return bytesLength;
	}

	/**
	 * 检验字符串是否未空, 如果是, 则返回给定的出错信息.
	 *
	 * @param input
	 *            输入的字符串
	 * @param errorMsg
	 *            出错信息
	 * @return 空串返回出错信息
	 */
	public static String isEmpty(String input, String errorMsg) {
		if (isEmpty(input)) {
			return errorMsg;
		}
		return "";
	}

	/**
	 * 得到文件的扩展名.
	 *
	 * @param fileName
	 *            需要处理的文件的名字.
	 * @return the extension portion of the file's name.
	 */
	public static String getExtension(String fileName) {
		if (fileName != null) {
			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(i + 1).toLowerCase();
			}
		}
		return "";
	}

	/**
	 * 得到文件的前缀名.
	 *
	 * @date 2005-10-18
	 *
	 * @param fileName
	 *            需要处理的文件的名字.
	 * @return the prefix portion of the file's name.
	 */
	public static String getPrefix(String fileName) {
		if (fileName != null) {
			fileName = fileName.replace('\\', '/');

			if (fileName.lastIndexOf("/") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1,
						fileName.length());
			}

			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(0, i);
			}
		}
		return "";
	}

	/**
	 * 得到文件的短路径, 不保护目录.
	 *
	 * @date 2005-10-18
	 *
	 * @param fileName
	 *            需要处理的文件的名字.
	 * @return the short version of the file's name.
	 */
	public static String getShortFileName(String fileName) {
		if (fileName != null) {
			String oldFileName = new String(fileName);

			fileName = fileName.replace('\\', '/');

			// Handle dir
			if (fileName.endsWith("/")) {
				int idx = fileName.indexOf('/');
				if (idx == -1 || idx == fileName.length() - 1) {
					return oldFileName;
				} else {
					return oldFileName
							.substring(idx + 1, fileName.length() - 1);
				}

			}
			if (fileName.lastIndexOf("/") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1,
						fileName.length());
			}

			return fileName;
		}
		return "";
	}

	/**
	 * 获取表单参数并做默认转码, 从 ISO8859-1 转换到 GBK.
	 *
	 * @author BeanSoft
	 * @date 2005-08-01
	 *
	 * @param request
	 *            HttpServletRequest 对象
	 * @param fieldName
	 *            参数名
	 * @return 取得的表单值
	 */
	public static String getParameter(HttpServletRequest request,
									  String fieldName) {
		// // 判断编码是否已经指定
		// String encoding = request.getCharacterEncoding();
		//
		// if("GBK".equalsIgnoreCase(encoding) ||
		// "GB2312".equalsIgnoreCase(encoding)) {
		// return request.getParameter(fieldName);
		// }
		//
		// return request(request, fieldName);
		// 2005-08-01 临时修改
		// try {
		// request.setCharacterEncoding("UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// // TODO auto generated try-catch
		// e.printStackTrace();
		// }

		return request.getParameter(fieldName);
	}

	// ------------------------------------ JSP 参数处理方法
	// ----------------------------------------------
	/** 一个与 ASP 类似的方法, 返回表单域的值, 并转换内码 */
	public static String request(HttpServletRequest request, String fieldName) {
		// POST 方法的参数有编码错误
		if (request.getMethod().equalsIgnoreCase("POST")) {
			// JSP Smart upload 的 request 对象没有中文问题
			// try {
			// if (request instanceof com.jspsmart.upload.Request) {
			// // 2004-04-02, Fix the encoding bug on SCO open server(Unix,
			// // os.name=OpenServer)
			// // when running jsp smart at tomcat 3.1.1,
			// // but on Windows and Linux no this problems
			// if (System.getProperty("os.name").toLowerCase().indexOf(
			// "openserver") != -1) {
			// return toChi(request.getParameter(fieldName));
			// } else {
			// return request.getParameter(fieldName);
			// }
			// }
			// } catch (Throwable ex) {
			// // Throwable -- 防止未导入 smartupload 类库时出错
			// System.err.println(ex);
			// }
			// 文件上传模式
			// if(isUploadMode) {
			// return request.getParameter(fieldName);
			// }
			// For Tomcat 4.0.6
			return toChi(request.getParameter(fieldName));
		}
		// 将通过 GET 方式发送的中文字符解码(但是必须使用 java.net.URLEncoder 进行中文字符参数的编码)
		// 解码时需使用内码转换, 也可使用反编码, 即: return
		// decode(request.getParameter(fieldName));
		// 问题: decode() 仅适用于 JDK 1.3 + Tomcat 4.0
		return toChi(request.getParameter(fieldName));
	}

	/** 如果表单的值是 null, 则返回 "", 避免 NullPointerException */
	public String request1(HttpServletRequest request, String fieldName) {
		String s = request(request, fieldName);
		if (s == null) {
			return "";
		}
		return s;
	}

	/**
	 * 获得指定表单域的值, 并将单个的 ' 换成 ''; SQL 规则:如果单引号中的字符串包含一个嵌入的引号， 可以使用两个单引号表示嵌入的单引号。
	 */
	public String requestSql(HttpServletRequest request, String fieldName) {
		return replaceSql(request1(request, fieldName));
	}

	/**
	 * 根据 Cookie 名称得到请求中的 Cookie 值, 需要事先给 _request 一个初始值; 如果 Cookie 值是 null, 则返回
	 * ""
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "";
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals(name)) {
				// 需要对 Cookie 中的汉字进行 URL 反编码, 适用版本: Tomcat 4.0
				return decode(cookie.getValue());
				// 不需要反编码, 适用版本: JSWDK 1.0.1
				// return cookie.getValue();
			}
		}
		// A cookie may not return a null value, may return a ""
		return "";
	}

	// 返回指定表单名的数组
	public String[] getParameterValues(HttpServletRequest request, String name) {
		// POST 方法的参数没有编码错误
		// if (request.getMethod().equalsIgnoreCase("POST")) {
		// 文件上传模式
		// if(isUploadMode) {
		// return request.getParameterValues(name);
		// }
		// -- For Tomcat 4.0
		// return request.getParameterValues(name);
		// -- For JSWDK 1.0.1
		/*
		 * String values[] = _request.getParameterValues(name); if(values !=
		 * null) { for(int i = 0; i < values.length; i++) { values[i] =
		 * toChi(values[i]); } } return values;
		 */
		// }
		// else {
		// 将通过 GET 方式发送的中文字符解码(但是必须使用 java.net.URLEncoder 进行中文字符参数的编码)
		// 解码时需使用内码转换, 也可使用反编码, 即: return decode(_request.getParameter(name));
		// 问题: decode() 仅适用于 JDK 1.3 + Tomcat 4.0
		String encoding = request.getCharacterEncoding();

		if ("GBK".equalsIgnoreCase(encoding)
				|| "GB2312".equalsIgnoreCase(encoding)) {
			return request.getParameterValues(name);
		}

		String values[] = request.getParameterValues(name);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				values[i] = toChi(values[i]);
			}
		}
		return values;
		// }
	}

	/**
	 * 删除指定的 Web 应用程序目录下所上传的文件
	 *
	 * @param application
	 *            JSP/Servlet 的 ServletContext
	 * @param filePath
	 *            相对文件路径
	 */
	public static void deleteFile(ServletContext application, String filePath) {
		if (!isEmpty(filePath)) {
			String physicalFilePath = application.getRealPath(filePath);
			if (!isEmpty(physicalFilePath)) {
				File file = new File(physicalFilePath);
				file.delete();
			}
		}
	}

	/**
	 * 在指定的 Web 应用程序目录下以指定路径创建文件
	 *
	 * @param application
	 *            JSP/Servlet 的 ServletContext
	 * @param filePath
	 *            相对文件路径
	 */
	public static boolean createFile(ServletContext application, String filePath) {
		if (!isEmpty(filePath)) {
			String physicalFilePath = application.getRealPath(filePath);
			if (!isEmpty(physicalFilePath)) {
				File file = new File(physicalFilePath);

				try {
					// 创建文件
					return file.createNewFile();
				} catch (IOException e) {
					System.err.println("Unable to create file " + filePath);
				}
			}
		}

		return false;
	}

	/**
	 * 在指定的 Web 应用程序目录下以指定路径创建目录.
	 *
	 * @param application
	 *            JSP/Servlet 的 ServletContext
	 * @param filePath
	 *            相对文件路径
	 */
	public static boolean createDir(ServletContext application, String filePath) {
		if (!isEmpty(filePath)) {
			String physicalFilePath = application.getRealPath(filePath);
			if (!isEmpty(physicalFilePath)) {
				try {
					// 创建目录
					File dir = new File(application
							.getRealPath(filePath));
					return dir.mkdirs();
				} catch (Exception e) {
					System.err
							.println("Unable to create directory " + filePath);
				}
			}
		}

		return false;
	}

	/**
	 * 检查指定的 Web 应用程序目录下的文件是否存在.
	 *
	 * @param application
	 *            JSP/Servlet 的 ServletContext
	 * @param filePath
	 *            相对文件路径
	 * @return boolean - 文件是否存在
	 */
	public static boolean checkFileExists(ServletContext application,
			String filePath) {
		if (!isEmpty(filePath)) {
			String physicalFilePath = application.getRealPath(filePath);
			if (!isEmpty(physicalFilePath)) {
				File file = new File(physicalFilePath);
				return file.exists();
			}
		}

		return false;
	}

	/**
	 * 获取文件图标名. Date: 2005-10
	 *
	 * @param application
	 *            JSP/Servlet 的 ServletContext
	 * @param iconDirPath
	 *            图标文件夹的路径
	 * @param fileName
	 *            需要处理的文件名
	 * @return 图标文件相对路径
	 */
	public static String getFileIcon(ServletContext application,
									 String iconDirPath, String fileName) {
		String ext = getExtension(fileName);
		String filePath = iconDirPath + ext + ".gif";
		// return filePath;

		if (checkFileExists(application, filePath)) {
			return filePath;
		}
		return iconDirPath + "file.gif";
	}

	/**
	 * 输出分页显示的结果.
	 *
	 * @param page
	 *            当前页面
	 * @param recordCount
	 *            所有结果
	 * @param pageSize
	 *            一页显示的多少
	 * @param pageCountSize
	 *            前后跳页的多少
	 * @param linkpageurl
	 *            连接页面的 URL 字符串
	 * @return 分页结果的字符串.
	 */
	public static String paging(int page, int recordCount, int pageSize,
								int pageCountSize, String linkpageurl) {
		int PageCount = -1; // 页面总数
		String LinkPageName = linkpageurl;
		String LinkText = "";
		int StartPage;
		int TempPage;
		int TempPageCount;
		TempPage = (page - 1) % pageCountSize; // 唱赣瘤 备窃
		StartPage = page - TempPage; // 矫累 其捞瘤 备窃
		TempPageCount = recordCount % pageSize;
		if (TempPageCount == 0) {
			PageCount = recordCount / pageSize;
		} else {
			PageCount = (recordCount / pageSize) + 1; // 傈眉 其捞瘤 荐
		}
		String txtPrev = " [前" + pageCountSize + "页] ";
		String txtNext = " [后" + pageCountSize + "页] ";
		String txtStart = " [首页] ";
		String txtEnd = " [末页] ";
		// 贸澜栏肺
		if (StartPage - 1 > 0) {
			LinkText += "<a href='" + LinkPageName + "&page=1' title='到此页'>"
					+ txtStart + "</a>";
		} else {
			LinkText += txtStart;
		}
		// 捞傈 10俺..
		if (StartPage - 1 > 0) {
			LinkText += "<a href='" + LinkPageName + "&page=" + (StartPage - 1)
					+ "' title='到第" + pageCountSize + "页'>" + txtPrev + "</a>";
		} else {
			LinkText += txtPrev;
		}
		for (int i = StartPage; i < StartPage + pageCountSize; i++) {
			if (i < PageCount + 1) {
				LinkText += "<a href='" + LinkPageName + "&page=";
				LinkText += i + "' title='" + i + "页'>";
				if (i == page) {
					LinkText += "<b>[" + i + "]</b>";
				} else {
					LinkText += "[" + i + "]";
				}
				LinkText += "</a>";
			}
		}
		// 中间页面
		if (StartPage + pageCountSize - PageCount - 1 < 0) {
			LinkText += "<a href='" + LinkPageName + "&page="
					+ (StartPage + pageCountSize) + "' title='到第"
					+ pageCountSize + "页'>" + txtNext + "</a>";
		} else {
			LinkText += txtNext;
		}
		// 最后一页
		if (StartPage + pageCountSize <= PageCount) {
			LinkText += "<a href='" + LinkPageName + "&page=" + PageCount
					+ "' title='最后一页'>" + txtEnd + "</a>";
		} else {
			LinkText += txtEnd;
		}
		return LinkText;
	}

	/**
	 * Gets the absolute pathname of the class or resource file containing the
	 * specified class or resource name, as prescribed by the current classpath.
	 *
	 * @param resourceName
	 *            Name of the class or resource name.
	 * @return the absolute pathname of the given resource
	 */
	public static String getPath(String resourceName) {

		if (!resourceName.startsWith("/")) {
			resourceName = "/" + resourceName;
		}

		// resourceName = resourceName.replace('.', '/');

		URL classUrl = new StringUtils().getClass().getResource(
				resourceName);

		if (classUrl != null) {
			// logger.debug("\nClass '" + className +
			// "' found in \n'" + classUrl.getFile() + "'");
			// logger.debug("\n资源 '" + resourceName +
			// "' 在文件 \n'" + classUrl.getFile() + "' 中找到.");

			return classUrl.getFile();
		}
		// logger.debug("\nClass '" + className +
		// "' not found in \n'" +
		// System.getProperty("java.class.path") + "'");
		// logger.debug("\n资源 '" + resourceName +
		// "' 没有在类路径 \n'" +
		// System.getProperty("java.class.path") + "' 中找到");
		return null;
	}

	// -----------------------------------------------------------
	// ---------- 字符串和数字转换工具方法, 2004.03.27 添加 --------
	// ------------------------------------------------------------
	public static byte getByte(HttpServletRequest httpservletrequest, String s) {
		if (httpservletrequest.getParameter(s) == null
				|| httpservletrequest.getParameter(s).equals("")) {
			return 0;
		}
		return Byte.parseByte(httpservletrequest.getParameter(s));
	}

	/**
	 * Reading a parameter as integer from the http servlet request.
	 *
	 */
	public static int getInt(HttpServletRequest httpservletrequest, String s) {
		if (httpservletrequest.getParameter(s) == null
				|| httpservletrequest.getParameter(s).equals("")) {
			return 0;
		}
		return Integer.parseInt(httpservletrequest.getParameter(s));
	}

	public static long getLong(HttpServletRequest httpservletrequest, String s) {
		if (httpservletrequest.getParameter(s) == null
				|| httpservletrequest.getParameter(s).equals("")) {
			return 0L;
		}
		return Long.parseLong(httpservletrequest.getParameter(s));
	}

	public static short getShort(HttpServletRequest httpservletrequest, String s) {
		if (httpservletrequest.getParameter(s) == null
				|| httpservletrequest.getParameter(s).equals("")) {
			return 0;
		}
		return Short.parseShort(httpservletrequest.getParameter(s));
	}

	/**
	 * 将 TEXT 文本转换为 HTML 代码, 已便于网页正确的显示出来.
	 *
	 * @param input
	 *            输入的文本字符串
	 * @return 转换后的 HTML 代码
	 */
	public static String textToHtml(String input) {
		if (isEmpty(input)) {
			return "";
		}

		input = replace(input, "<", "&lt;");
		input = replace(input, ">", "&gt;");

		input = replace(input, "\n", "<br>");
		input = replace(input, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		input = replace(input, "\"", "\\\"");
		input = replace(input, "'", "&acute;");

		return input;
	}

	public static String toQuoteMark(String s) {
		s = replaceString(s, "'", "&#39;");
		s = replaceString(s, "\"", "&#34;");
		s = replaceString(s, "\r\n", "\n");
		log.info(s);
		return s;
	}

	public static String replaceChar(String s, char c, char c1) {
		if (s == null) {
			return "";
		}
		return s.replace(c, c1);
	}

	public static String replaceString(String s, String s1, String s2) {
		if (s == null || s1 == null || s2 == null) {
			return "";
		}
		return s.replaceAll(s1, s2);
	}

	public static String toHtml(String s) {
		s = replaceString(s, "<", "&#60;");
		s = replaceString(s, ">", "&#62;");
		return s;
	}

	public static String toBR(String s) {
		s = replaceString(s, "\n", "<br>\n");
		s = replaceString(s, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		s = replaceString(s, "  ", "&nbsp;&nbsp;");
		return s;
	}

	public static String toSQL(String s) {
		s = replaceString(s, "\r\n", "\n");
		return s;
	}

	public static String replaceEnter(String s) throws NullPointerException {
		return s.replaceAll("\n", "<br>");
	}

	public static String replacebr(String s) throws NullPointerException {
		return s.replaceAll("<br>", "\n");
	}

	public static String replaceQuote(String s) throws NullPointerException {
		return s.replaceAll("'", "''");
	}

	public static String replaceSql(String sql, String[] sqlParam) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		int index = 0;
		for (int i = 0; i < sqlBuffer.length(); i++) {
			if (sqlBuffer.substring(i, i + 1).equals("?")) {
				sqlBuffer.deleteCharAt(i);
				sqlBuffer.insert(i, sqlParam[index]);
				index++;
			}
		}
		return sqlBuffer.toString();
	}

	@SuppressWarnings("null")
	public static String GetPageContent(String pageURL) {
		String pageContent = "";
		BufferedReader in = null;
		InputStreamReader isr = null;
		InputStream is = null;
		PrintWriter pw = null;
		HttpURLConnection huc = null;
		try {
			URL url = new URL(pageURL);
			// 创建 URL
			huc = (HttpURLConnection) url.openConnection();
			is = huc.getInputStream();
			isr = new InputStreamReader(is);
			in = new BufferedReader(isr);
			String line = null;
			while (((line = in.readLine()) != null)) {
				if (line.length() == 0)
					continue;
				pageContent += line;
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally { // 无论如何都要关闭流
			try {
				is.close();
				isr.close();
				in.close();
				huc.disconnect();
				pw.close();
			} catch (Exception e) {
			}
		}
		return pageContent;
	}

	/**
	 * 判断字符串是否为Null或空字符串
	 *
	 * @param s
	 *            要判断的字符串
	 * @return String true-是空字符串,false-不是空字符串
	 */
	public static boolean nil(String s) {
		if (s == null || s.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 转换String到boolean
	 */
	public static boolean parseBoolean(String flag) {
		if (nil(flag))
			return false;
		else if (flag.equals("true") || flag.equals("1") || flag.equals("是")
				|| flag.equals("yes"))
			return true;
		else if (flag.equals("false") || flag.equals("0") || flag.equals("否")
				|| flag.equals("no"))
			return false;
		return false;
	}

	/*
	 * 为分页接收查询条件 转 成 sql 子句 key 或 value 有一个为空（“”）则不进行拼接
	 */
	public static String map2SqlStr(Map valueMap) {
		String sqlStr = "";
		Set setEntriy = valueMap.entrySet();
		for (Iterator it = setEntriy.iterator(); it.hasNext();) {
			Map.Entry obj = (Map.Entry) it.next();
			Object key = obj.getKey();
			Object value = obj.getValue();
			if ((!isEmpty(key.toString())) && (!isEmpty(value.toString()))) {
				sqlStr += " and " + key.toString() + "='" + value.toString()
						+ "'";
			}
		}

		return sqlStr;
	}

	public static String getRandString(int length) {
		StringBuffer s = new StringBuffer("" + (new Date().getTime()));
		Random r = new Random(10);
		s.append(Math.abs(r.nextInt()));
		if (s.toString().length() > length)
			s.substring(0, length);
		return s.toString();
	}

	// ============================================================================================

	public static boolean contains(String s, String text, String delimiter) {
		if ((s == null) || (text == null) || (delimiter == null)) {
			return false;
		}

		if (!s.endsWith(delimiter)) {
			s += delimiter;
		}

		int pos = s.indexOf(delimiter + text + delimiter);

		if (pos == -1) {
			if (s.startsWith(text + delimiter)) {
				return true;
			}

			return false;
		}

		return true;
	}

	public static int count(String s, String text) {
		if ((s == null) || (text == null)) {
			return 0;
		}

		int count = 0;

		int pos = s.indexOf(text);

		while (pos != -1) {
			pos = s.indexOf(text, pos + text.length());
			count++;
		}

		return count;
	}

	public static String merge(String array[], String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < array.length; i++) {
			sb.append(array[i].trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String read(ClassLoader classLoader, String name)
			throws IOException {

		return read(classLoader.getResourceAsStream(name));
	}

	public static String read(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuffer sb = new StringBuffer();
		String line = null;

		while ((line = br.readLine()) != null) {
			sb.append(line).append('\n');
		}

		br.close();

		return sb.toString().trim();
	}

	public static String read(InputStream is, String code) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is, code));

		StringBuffer sb = new StringBuffer();
		String line = null;

		while ((line = br.readLine()) != null) {
			sb.append(line).append("\r\n");
		}

		br.close();

		return sb.toString().trim();
	}

	public static String remove(String s, String remove, String delimiter) {
		if ((s == null) || (remove == null) || (delimiter == null)) {
			return null;
		}

		if (s != null && s.length() > 0 && !s.endsWith(delimiter)) {
			s += delimiter;
		}

		while (contains(s, remove, delimiter)) {
			int pos = s.indexOf(delimiter + remove + delimiter);

			if (pos == -1) {
				if (s.startsWith(remove + delimiter)) {
					s = s.substring(remove.length() + delimiter.length(), s
							.length());
				}
			} else {
				s = s.substring(0, pos)
						+ s.substring(pos + remove.length()
								+ delimiter.length(), s.length());
			}
		}

		return s;
	}

	public static String replace(String s, String[] oldSubs, String[] newSubs) {
		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replace(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	public static String reverse(String s) {
		if (s == null) {
			return null;
		}

		char[] c = s.toCharArray();
		char[] reverse = new char[c.length];

		for (int i = 0; i < c.length; i++) {
			reverse[i] = c[c.length - i - 1];
		}

		return new String(reverse);
	}

	public static String shorten(String s) {
		return shorten(s, 20);
	}

	public static String shorten(String s, int length) {
		return shorten(s, length, "..");
	}

	public static String shorten(String s, String suffix) {
		return shorten(s, 20, suffix);
	}

	public static String shorten(String s, int length, String suffix) {
		if (s == null || suffix == null) {
			return null;
		}

		if (s.length() > length) {
			s = s.substring(0, length) + suffix;
		}

		return s;
	}

	public static boolean[] split(String s, String delimiter, boolean x) {
		String[] array = split(s, delimiter);
		boolean[] newArray = new boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			boolean value = x;

			try {
				value = Boolean.valueOf(array[i]).booleanValue();
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static double[] split(String s, String delimiter, double x) {
		String[] array = split(s, delimiter);
		double[] newArray = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			double value = x;

			try {
				value = Double.parseDouble(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static float[] split(String s, String delimiter, float x) {
		String[] array = split(s, delimiter);
		float[] newArray = new float[array.length];

		for (int i = 0; i < array.length; i++) {
			float value = x;

			try {
				value = Float.parseFloat(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static int[] split(String s, String delimiter, int x) {
		String[] array = split(s, delimiter);
		int[] newArray = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			int value = x;

			try {
				value = Integer.parseInt(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static long[] split(String s, String delimiter, long x) {
		String[] array = split(s, delimiter);
		long[] newArray = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			long value = x;

			try {
				value = Long.parseLong(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static short[] split(String s, String delimiter, short x) {
		String[] array = split(s, delimiter);
		short[] newArray = new short[array.length];

		for (int i = 0; i < array.length; i++) {
			short value = x;

			try {
				value = Short.parseShort(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static final String stackTrace(Throwable t) {
		String s = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			t.printStackTrace(new PrintWriter(baos, true));
			s = baos.toString();
		} catch (Exception e) {
		}

		return s;
	}

	public static boolean startsWith(String s, char begin) {
		return startsWith(s, (new Character(begin)).toString());
	}

	public static boolean startsWith(String s, String begin) {
		if ((s == null) || (begin == null)) {
			return false;
		}

		if (begin.length() > s.length()) {
			return false;
		}

		String temp = s.substring(0, begin.length());

		if (temp.equalsIgnoreCase(begin)) {
			return true;
		} else {
			return false;
		}
	}

	public static String wrap(String text) {
		return wrap(text, 80);
	}

	public static String wrap(String text, int width) {
		if (text == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader br = new BufferedReader(new StringReader(text));

			String s = "";

			while ((s = br.readLine()) != null) {
				if (s.length() == 0) {
					sb.append("\n");
				} else {
					while (true) {
						int pos = s.lastIndexOf(' ', width);

						if ((pos == -1) && (s.length() > width)) {
							sb.append(s.substring(0, width));
							sb.append("\n");

							s = s.substring(width, s.length()).trim();
						} else if ((pos != -1) && (s.length() > width)) {
							sb.append(s.substring(0, pos));
							sb.append("\n");

							s = s.substring(pos, s.length()).trim();
						} else {
							sb.append(s);
							sb.append("\n");

							break;
						}
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return sb.toString();
	}

	public static String getPassword(int length, String key) {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			sb.append(key.charAt((int) (Math.random() * key.length())));
		}

		return sb.toString();
	}

	public static String getPassword(int length) {
		String key = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		return getPassword(length, key);
	}

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			System.err.print("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if (((int) encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	public static String getSubString(String str, String startStr, String endStr) {
		if (isEmpty(str))
			return null;
		int start = str.indexOf(startStr);
		int end = str.indexOf(endStr);
		if (end > start)
			return str.substring(start, end + 1);
		return null;
	}

	public static boolean isdate(String strDate) {
		/*
		 * String str="国际事务部授权通知 2010-7-16"; String strmid=
		 * str.substring(str.length()-9,str.length());
		 */
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher matcher = pattern.matcher(strDate);
		// System.out.println(matcher.matches());
		return matcher.matches();
	}

	/**
	 * 格式化string
	 * 
	 * @param str
	 * @param param
	 * @return
	 */
	public static String stringFormat(String str, String[] param) {
		MessageFormat format = new MessageFormat(str);
		return format.format(param);
	}

	/***************************************************************************
	 * 获取操作系统
	 */
	public static String getClientOS(String userAgent) {
		String cos = "unknow os";
		Pattern p = Pattern.compile(".*(Windows NT 6\\.1).*");
		Matcher m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win 7";
			return cos;
		}

		p = Pattern.compile(".*(Windows NT 5\\.1|Windows XP).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "WinXP";
			return cos;
		}

		p = Pattern.compile(".*(Windows NT 5\\.2).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win2003";
			return cos;
		}

		p = Pattern.compile(".*(Win2000|Windows 2000|Windows NT 5\\.0).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win2000";
			return cos;
		}

		p = Pattern.compile(".*(Mac|apple|MacOS8).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "MAC";
			return cos;
		}

		p = Pattern.compile(".*(WinNT|Windows NT).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "WinNT";
			return cos;
		}

		p = Pattern.compile(".*Linux.*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Linux";
			return cos;
		}

		p = Pattern.compile(".*(68k|68000).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Mac68k";
			return cos;
		}

		p = Pattern
				.compile(".*(9x 4.90|Win9(5|8)|Windows 9(5|8)|95/NT|Win32|32bit).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			cos = "Win9x";
			return cos;
		}

		return cos;
	}

	/***************************************************************************
	 * 浏览器信息
	 */
	public static String getClientBrowser(String userAgent) {
		String browser = "";
		userAgent = userAgent.toLowerCase();
		if (userAgent.indexOf("msie") > -1) {
			if (userAgent.indexOf("msn") > -1)
				browser = "MSN Explorer";
			else
				browser = "Internet Explorer";
		} else if (userAgent.indexOf("opera") > -1)
			browser = "Opera";
		else if (userAgent.indexOf("konqueror") > -1)
			browser = "KDE Konqueror";
		else if (userAgent.indexOf("x11 ") > -1)
			browser = "X Windows Browser";
		else if (userAgent.indexOf("firefox") > -1)
			browser = "Firefox";
		else if (userAgent.indexOf("mozilla") > -1)
			browser = "Netscape Navigator";
		else if (userAgent.indexOf("safari") != -1)
			browser = "Safari";
		else
			browser = "Unknown";
		return browser;
	}

	public static String toChinese(String strvalue, String charset) {
		try {
			if (charset == null || charset.equals(""))
				charset = "UTF-8";
			strvalue = new String(strvalue.getBytes("ISO8859_1"), charset);
			return strvalue;
		} catch (Exception e) {
			return null;
		}
	}

	// =====================判断邮件email是否正确格式

	public static boolean checkemail(String email) {
		Pattern pattern = Pattern.compile("\\w+[\\w]*@[\\w]+\\.[\\w]+$");
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	// =====================判断密码是否正确格式
	public static boolean checkPassword(String pwd) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]{6,20}$");
		Matcher matcher = pattern.matcher(pwd);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	// 字符串是否含有中文
	public static boolean validateAccount(String str) {
		return str.matches("[A-Za-z0-9_]{4,20}");
	}

	
	/**
	 * 将null转为指定的字符，默认为空
	 * @return
	 */
	public static String null2str(String source, String target) {
		if(null == target) {
			target = "";
		} 
		
		if(null == source) {
			return target;
		}
		return source;
	}

    /**
     * 传入两个数组，返回两个数组的交集，如果有一个数组为空返回null
     * 如果交集为空，boll为true返回oneStrs，flase返回towStrs
     * @param oneStrs
     * @param twoStrs
     * @param bool
     * @return	
     * 
     */
    public static String[] arrayIntersection(String[] oneStrs, String[] twoStrs, boolean bool ){
    	String[] newstrs = null;
    	List<String> array = new ArrayList<String>();
    	if(oneStrs.length!=0&&twoStrs.length!=0){
    		for (String str : oneStrs) {
				for (String str2 : twoStrs) {
					if(str.equals(str2)){
						array.add(str2);
					}
				}
			}
    		if(CollectionUtils.isEmpty(array)){
    			if(bool){
        			newstrs = oneStrs;
        		}else{
        			newstrs = twoStrs;	
        		}
    		}else{
    			newstrs = (String[]) array.toArray(new String[array.size()]);
    		}
    	}
    	return newstrs;
    }
    
    
    /**
	 * UUID 生成器 
	 * @return uuid
	 * @date 2014-09-15
	 * @author zhaojiafu
	 */
	public static String UUIDGenerator() {
		UUID uuid = UUID.randomUUID();
		String target = uuid.toString().replace("-", "");
		
		return target;
	}
    
	
	// SensitiveWordsUtil only.
	public static void main(String[] args) throws Exception {
//		 String strTest = "123,33,44,AA,*,222,333,444,*,555,666,777,*";
//		 String str[] = split(strTest, "*");
//		 String[] stros = StringToStringArray(str[0], ",");
//		 log.debug(String.valueOf(str.length));
//		 log.debug(String.valueOf(stros.length));
//
//		 System.out.println(isMobile("18612834872"));

		System.out.println(StringUtils.isNotBlank("sss"));
	}
}
