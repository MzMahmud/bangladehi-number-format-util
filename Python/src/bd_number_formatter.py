def get_formatted_number(num_str: str) -> str:
    if not num_str:
        return ''

    formatted_number = ''
    if num_str.startswith('-') or num_str.startswith('+'):
        formatted_number = num_str[0]
        num_str = num_str[1:]

    decimal_split = num_str.split('.')

    formatted_number += get_formatted_integer(decimal_split[0])

    if len(decimal_split) > 1:
        formatted_number += f'.{decimal_split[1]}'

    return formatted_number


def get_formatted_integer(num_str: str) -> str:
    if len(num_str) <= 7:
        return get_formatted_number_below_crore(num_str)

    lakh, crore = get_lakh_crore_split(num_str)
    return f"{get_formatted_integer(crore)},{get_formatted_number_below_crore(lakh)}"


def get_lakh_crore_split(num_str: str) -> tuple:
    if len(num_str) <= 7:
        return num_str, ""

    reversed_digit = list(reversed(num_str))

    lakh = ''.join(reversed(reversed_digit[0:7]))
    crore = ''.join(reversed(reversed_digit[7:]))
    return lakh, crore


def get_formatted_number_below_crore(num_str: str) -> str:
    reverse_formatted_number = []
    for index, char in enumerate(reversed(num_str)):
        if is_separator(index + 1):
            reverse_formatted_number.append(",")
        reverse_formatted_number.append(char)
    return ''.join(reversed(reverse_formatted_number))


def is_separator(char_order: int) -> bool:
    return char_order == 4 or (char_order > 4 and char_order % 2 == 0)
